// Isomorphic Javascript: acts as Node.js server OR Browser frontend script.
// This confines the entire application architecture cleanly into the requested 3 files.

if (typeof window === 'undefined') {
    // ==========================================
    // NODE.JS SERVER ENVIRONMENT
    // ==========================================
    const http = require('http');
    const fs = require('fs');
    const path = require('path');

    const PORT = 3000;

    const serveFile = (res, filePath, contentType) => {
        fs.readFile(filePath, (err, content) => {
            if (err) {
                res.writeHead(500);
                res.end('Server Error');
                return;
            }
            res.writeHead(200, { 'Content-Type': contentType });
            res.end(content, 'utf-8');
        });
    };

    const server = http.createServer((req, res) => {
        // Performance optimizations for high-throughput dictionary attacks
        req.socket.setNoDelay(true); // Disable Nagle's algorithm for lowest possible latency
        res.setHeader('Connection', 'keep-alive'); // Heavily encourage TCP socket reuse

        // Enable CORS in case the Java application or other clients require it for local testing
        res.setHeader('Access-Control-Allow-Origin', '*');
        res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS');
        res.setHeader('Access-Control-Allow-Headers', 'Content-Type');

        if (req.method === 'OPTIONS') {
            res.writeHead(204);
            res.end();
            return;
        }

        if (req.method === 'GET') {
            if (req.url === '/' || req.url === '/index.html') {
                serveFile(res, path.join(__dirname, 'index.html'), 'text/html');
            } else if (req.url === '/style.css') {
                serveFile(res, path.join(__dirname, 'style.css'), 'text/css');
            } else if (req.url === '/logic.js') {
                serveFile(res, path.join(__dirname, 'logic.js'), 'application/javascript');
            } else {
                res.writeHead(404);
                res.end('Not Found');
            }
        } else if (req.method === 'POST' && req.url === '/login') {
            let body = '';
            req.on('data', chunk => {
                body += chunk.toString();
            });
            req.on('end', () => {
                let parsed;
                try {
                    parsed = JSON.parse(body);
                } catch(e) {
                    res.writeHead(400, { 'Content-Type': 'application/json' });
                    res.end(JSON.stringify({ success: false, status: 400, message: "Bad Request" }));
                    return;
                }

                // Hardcoded dummy credentials for local lab
                if (parsed.username === 'admin' && parsed.password === 'The_Goat_CR7') {
                    res.writeHead(200, { 'Content-Type': 'application/json' });
                    res.end(JSON.stringify({
                        success: true,
                        status: 200,
                        message: "Login successful"
                    }));
                } else {
                    res.writeHead(404, { 'Content-Type': 'application/json' });
                    res.end(JSON.stringify({
                        success: false,
                        status: 404,
                        message: "Invalid username or password"
                    }));
                }
            });
        } else {
            res.writeHead(404);
            res.end('Not Found');
        }
    });

    // Bind strictly to localhost (127.0.0.1) as requested for security
    server.listen(PORT, '127.0.0.1', () => {
        console.log(`[Dictionary Attack Lab] Local server running at http://127.0.0.1:${PORT}/`);
        console.log(`[API Endpoint] POST http://127.0.0.1:${PORT}/login`);
        console.log(`[Credentials] Username: admin | Password: The_Goat_CR7`);
    });

} else {
    // ==========================================
    // BROWSER FRONTEND ENVIRONMENT
    // ==========================================
    document.addEventListener('DOMContentLoaded', () => {
        const form = document.getElementById('login-form');
        const responsePanel = document.getElementById('response-panel');
        const responseCode = document.getElementById('response-code');
        const statusHeader = document.getElementById('status-header');

        form.addEventListener('submit', async (e) => {
            e.preventDefault();
            
            const usernameInput = document.getElementById('username');
            const passwordInput = document.getElementById('password');
            const loginBtn = document.getElementById('login-btn');
            
            const username = usernameInput.value;
            const password = passwordInput.value;

            // Disable form during submission
            loginBtn.disabled = true;
            loginBtn.textContent = 'Authenticating...';

            try {
                // Submit to local endpoint (hardcoded to port 3000 so Live Server works)
                const response = await fetch('http://127.0.0.1:3000/login', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({ username, password })
                });

                const data = await response.json();

                // Update UI based on response
                responsePanel.classList.remove('hidden', 'success', 'error');
                
                if (response.ok && data.success) {
                    responsePanel.classList.add('success');
                    statusHeader.textContent = `HTTP ${response.status} OK`;
                } else {
                    responsePanel.classList.add('error');
                    statusHeader.textContent = `HTTP ${response.status} Not Found`;
                }

                // Format JSON for educational display in developer console style
                responseCode.textContent = JSON.stringify(data, null, 4);

            } catch (error) {
                console.error("Network error:", error);
                responsePanel.classList.remove('hidden', 'success');
                responsePanel.classList.add('error');
                statusHeader.textContent = "Network Error";
                responseCode.textContent = "Make sure the Node.js server is running.\nRun: node src/server/logic.js";
            } finally {
                loginBtn.disabled = false;
                loginBtn.textContent = 'Login';
            }
        });
    });
}
