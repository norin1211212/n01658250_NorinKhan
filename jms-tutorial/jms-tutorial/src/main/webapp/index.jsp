<!DOCTYPE html>
<html>
<head>
    <title>JMS Message Sender</title>
    <script>
        async function sendMessage() {
            const message = document.getElementById("message").value;
            const response = await fetch("http://localhost:8080//jms-tutorial-1.0send?message=" + encodeURIComponent(message));
            const receivedMessage = await response.text();
            document.getElementById("receivedMessage").textContent = receivedMessage;
        }
    </script>
</head>
<body>
<h2>JMS Message Sender</h2>
<label for="message">Enter Message:</label>
<input type="text" id="message" name="message" required>
<button type="button" onclick="sendMessage()">Send Message</button>

<h3>Received Message:</h3>
<p id="receivedMessage">No message received yet.</p>
</body>
</html>
