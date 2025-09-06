const express = require("express");
const http = require("http");
const WebSocket = require("ws");
const axios = require("axios");

const app = express();
const server = http.createServer(app);
const wss = new WebSocket.Server({ server });

let users = {}; // { userId: { ws, username, room } }

// 🔹 Load users from Spring Boot DB at startup
async function loadUsersFromSpring() {
  try {
    const res = await axios.get("http://localhost:8080/api/users"); // Spring Boot API
    const userList = res.data;

    userList.forEach(u => {
      users[u.id] = { ws: null, username: u.username, room: null };
    });

    console.log("Users loaded from Spring Boot:", Object.keys(users));
  } catch (err) {
    console.error("Error fetching users:", err.message);
  }
}

// Load once at server start
loadUsersFromSpring();

wss.on("connection", (ws) => {
  console.log("New client connected");

  let userId;

  ws.on("message", (msg) => {
    try {
      const data = JSON.parse(msg);

      // 🔹 Register existing DB user
      if (data.type === "REGISTER") {
        const dbUser = users[data.user_id];
        if (dbUser) {
          dbUser.ws = ws; // Attach WebSocket to this DB user
          userId = data.user_id;
          ws.send(JSON.stringify({ type: "REGISTERED", userId }));
          broadcastUserList();
        } else {
          ws.send(JSON.stringify({ type: "ERROR", message: "Invalid userId" }));
        }
      }

      // 🔹 Join room
      if (data.type === "JOIN_ROOM") {
        if (users[userId]) {
          users[userId].room = data.room;
          ws.send(JSON.stringify({ type: "JOINED_ROOM", room: data.room }));
          broadcastRoomMessage(data.room, `${users[userId].username} joined the room`);
        }
      }

      // 🔹 Send message to room
      if (data.type === "ROOM_MESSAGE") {
        const user = users[userId];
        if (user && user.room) {
          broadcastRoomMessage(user.room, `${user.username}: ${data.message}`);
        }
      }

      // 🔹 Direct message
      if (data.type === "DIRECT_MESSAGE") {
        const target = users[data.targetId];
        const sender = users[userId];
        if (target && sender && target.ws) {
          target.ws.send(JSON.stringify({
            type: "DIRECT_MESSAGE",
            from: sender.username,
            message: data.message
          }));
        }
      }

    } catch (err) {
      console.error("Invalid message", err);
    }
  });

  ws.on("close", () => {
    if (userId && users[userId]) {
      const room = users[userId].room;
      const username = users[userId].username;
      users[userId].ws = null; // Keep user in DB but mark offline
      if (room) broadcastRoomMessage(room, `${username} left the room`);
      broadcastUserList();
    }
  });
});

// 🔹 Broadcast all users (with online/offline status)
function broadcastUserList() {
  const payload = {
    type: "USER_LIST",
    users: Object.entries(users).map(([id, u]) => ({
      id,
      username: u.username,
      room: u.room,
      online: !!u.ws
    }))
  };
  broadcastAll(payload);
}

// 🔹 Broadcast to everyone
function broadcastAll(data) {
  const str = JSON.stringify(data);
  for (let uid in users) {
    if (users[uid].ws) {
      users[uid].ws.send(str);
    }
  }
}

// 🔹 Broadcast to a specific room
function broadcastRoomMessage(room, text) {
  const payload = { type: "ROOM_MESSAGE", message: text };
  for (let uid in users) {
    if (users[uid].room === room && users[uid].ws) {
      users[uid].ws.send(JSON.stringify(payload));
    }
  }
}

app.get("/", (req, res) => {
  res.send("WebSocket Chat Server");
});

const PORT = 7070;
server.listen(PORT, () => console.log(` Server running at http://localhost:${PORT}`));
