# Dictionary Attack Lab

A desktop-based educational security tool that demonstrates how a dictionary attack interacts with an HTTP authentication endpoint. The project provides a JavaFX interface for configuring a target, selecting a wordlist, monitoring attack progress, and controlling the attack lifecycle.

> **Educational and authorized-use notice:** This project is intended for security education, laboratory environments, coursework, and testing systems you own or have explicit permission to assess. Do not use it against third-party accounts, services, or infrastructure without authorization.

## Overview

Dictionary Attack Lab demonstrates the mechanics of an online dictionary attack:

1. Load a wordlist.
2. Read candidate passwords sequentially.
3. Build an HTTP authentication request for each candidate.
4. Send the request to the configured login endpoint.
5. Inspect the HTTP response.
6. Stop when the configured endpoint returns a successful authentication response.
7. Display progress, candidate information, timing, and request status in the JavaFX dashboard.

The project also includes a small Node.js authentication server designed specifically for local testing.

## Features

- JavaFX desktop interface
- Configurable login URL
- Configurable username
- Local wordlist selection
- Large wordlist support through sequential file reading
- Progress percentage and progress bar
- Attempt counter
- Current wordlist line
- Current candidate display
- Elapsed-time display
- Attempts-per-time metric
- Attack log
- Pause and resume controls
- Attack termination
- Restartable attack sessions
- Local Node.js test server
- HTTP status-code based authentication result
- Lightweight, dependency-minimal implementation

## Architecture

```text
┌───────────────────────────────┐
│       JavaFX Desktop UI       │
│                               │
│  Target Configuration         │
│  Wordlist Selection           │
│  Progress / Statistics        │
│  Attack Log                   │
│  Pause / Resume / Terminate   │
└───────────────┬───────────────┘
                │
                │ Java Task / Background Thread
                ▼
┌───────────────────────────────┐
│       Attack Controller       │
│                               │
│  Wordlist Reader              │
│  HTTP Request Builder         │
│  Response Handling             │
│  Pause / Resume Synchronizer  │
└───────────────┬───────────────┘
                │
                │ HTTP POST
                ▼
┌───────────────────────────────┐
│      Node.js Test Server      │
│                               │
│          POST /login          │
│                               │
│   Validate username/password  │
│   Return HTTP response        │
└───────────────────────────────┘
```

The JavaFX interface and attack engine are separated so the HTTP attack process can execute on a background thread without blocking the UI.

## Project Structure

```text
DictionaryAttack/
├── src/
│   ├── controller/
│   │   └── AttackController.java
│   │
│   ├── main/
│   │   ├── DictionaryAttackApp.java
│   │   └── Main.java
│   │
│   ├── server/
│   │   ├── index.html
│   │   ├── logic.js
│   │   └── style.css
│   │
│   └── styling/
│       └── stylings.java
│
├── wordlists/
│   ├── words.txt
│   ├── wordList1.txt
│   └── rockyou.txt
│
├── .vscode/
│   └── settings.json
│
└── Readme.md
```

## Requirements

### Desktop application

- Java Development Kit (JDK)
- JavaFX SDK
- A Java IDE or editor such as VS Code, IntelliJ IDEA, or Eclipse
- Java HTTP Client (`java.net.http`), available in modern JDK versions

### Test server

- Node.js
- A modern web browser if you want to use the included browser login page

No external database is required.

## Running the Local Test Server

The included server binds specifically to localhost for the lab environment.

From the project root:

```bash
node src/server/logic.js
```

The server exposes:

```text
POST http://127.0.0.1:3000/login
```

The server is intended to be used as the controlled authentication target for the JavaFX application.

When the server starts successfully, it reports the local endpoint in the terminal.

### Important

The test server is deliberately configured for local experimentation. If you adapt this project for any network-accessible environment, do not reuse the demonstration authentication logic or hard-coded credentials. Implement proper authentication, password hashing, authorization, rate limiting, logging, and HTTPS.

## Running the JavaFX Application

Launch:

```text
src/main/Main.java
```

The application starts the JavaFX dashboard through `AttackController`.

Before starting an attack, configure:

- **Login URL**: the authentication endpoint
- **Username**: the account identifier used for the test
- **Wordlist File**: the candidate-password file

For the included local server, the endpoint is:

```text
http://127.0.0.1:3000/login
```

Start the Node.js server first, then start the JavaFX application.

## Attack Workflow

The normal workflow is:

```text
Start Attack
     │
     ▼
Read wordlist
     │
     ▼
Build authentication request
     │
     ▼
Send HTTP POST
     │
     ▼
Inspect response
     │
     ├── Authentication succeeds
     │        │
     │        ▼
     │     Stop attack
     │
     └── Authentication fails
              │
              ▼
        Read next candidate
```

The attack reads the wordlist sequentially rather than loading the entire dictionary into an in-memory collection.

This is particularly useful when working with large wordlists.

## Pause and Resume

The attack controller implements pause/resume synchronization using:

- `ReentrantLock`
- `Condition`
- a pause state flag

Pausing does not terminate the worker thread. The attack waits at a safe point and resumes from the current position when the Resume control is activated.

An HTTP request that is already in progress is allowed to finish before the attack enters its paused state.

## Termination and Restart

Termination is separate from pause.

A terminated attack can be started again as a fresh run. A new attack opens the selected wordlist again, causing processing to begin from the first candidate rather than continuing from the previous position.

The UI state is also reset when a new attack session is started.

## Performance Considerations

Large wordlists can contain millions of candidates. This project therefore uses several measures to keep the JavaFX interface responsive:

### Sequential wordlist processing

The attack uses `BufferedReader` to process the wordlist line by line rather than storing the entire dictionary in an `ArrayList`.

```java
try (BufferedReader reader =
        new BufferedReader(new FileReader(path))) {

    String pass;

    while ((pass = reader.readLine()) != null) {
        // Process candidate
    }
}
```

### Background execution

The attack engine runs inside a JavaFX `Task` and a background thread.

This prevents the blocking HTTP operation from running on the JavaFX Application Thread.

### Controlled UI updates

The dashboard does not need to redraw itself for every candidate. UI updates are throttled to reduce pressure on the JavaFX event queue.

JavaFX controls are updated through `Platform.runLater(...)` because JavaFX UI elements must be modified from the JavaFX Application Thread.

## HTTP Request Format

The attack engine sends an HTTP `POST` request with JSON data.

The request includes:

```json
{
  "username": "admin",
  "gotrue_meta_security": "{}",
  "password": "candidate"
}
```

The exact request structure is controlled by the attack engine and is intended to match the included demonstration server.

## Response Handling

The demonstration server uses the HTTP response to indicate whether authentication succeeded.

The Java client checks the HTTP status code:

```java
if (responseCode == 200) {
    // Authentication succeeded
}
```

Non-success responses continue the candidate search.

This behavior is intentionally simplified for educational purposes. Real authentication systems should not rely on simplistic status-code behavior alone.

## Security Considerations

This project demonstrates an important security principle:

> **An authentication endpoint should be considered discoverable and directly reachable by an attacker.**

A production frontend does not make its backend API secret.

For example:

```text
Frontend
https://example.com
       │
       ▼
Backend API
https://api.example.com/login
```

A user can inspect browser network requests and discover the API endpoint. An attacker can also reproduce the underlying HTTP request without using the website's UI.

Therefore, security should come from controls such as:

- Strong password policies
- Secure password hashing
- HTTPS/TLS
- Rate limiting
- Login throttling
- Account lockout or progressive delays
- Multi-factor authentication
- Session security
- Monitoring and alerting
- IP/device reputation controls where appropriate
- Secure error handling
- Proper authentication and authorization design

**Do not rely on hiding the API URL.**

## Ethical Use

Use this project only in environments where you have explicit authorization.

Appropriate use cases include:

- University security assignments
- Personal security labs
- CTF-style environments
- Testing a locally hosted authentication server
- Demonstrating dictionary attacks to students
- Testing applications you own or are authorized to assess

Do not use the tool to attempt authentication against accounts, websites, APIs, or systems belonging to other people or organizations without permission.

## Limitations

This project is intentionally a learning-oriented implementation rather than a production penetration-testing framework.

Current limitations include:

- Sequential HTTP requests
- No distributed attack capability
- No proxy rotation
- No advanced response fingerprinting
- No CAPTCHA handling
- No account lockout detection
- No automatic rate-limit adaptation
- No persistent attack session storage
- Demonstration server uses simplified authentication logic
- Wordlist processing depends on the local filesystem and network response time

These limitations are intentional or appropriate for the project's educational scope.

## Learning Objectives

This project demonstrates practical concepts from application and network security, including:

- Dictionary attacks
- Authentication attack surfaces
- HTTP request construction
- HTTP status codes
- Client-server architecture
- API endpoints
- Java HTTP Client
- JavaFX concurrency
- Background tasks
- Thread synchronization
- Pause/resume state management
- File streaming
- UI responsiveness
- Basic attack telemetry and visualization
- Security through controls rather than obscurity

## Disclaimer

This software is provided for educational and authorized security-testing purposes only.

The author is not responsible for misuse, unauthorized access, data loss, service disruption, or any damage resulting from use of this project outside an explicitly authorized environment.

Always obtain permission before testing a system you do not own.

## License

If you publish this repository publicly, add the license that matches how you want others to use, modify, and redistribute the project.

For example, a permissive open-source project could use the MIT License. If you choose MIT, add a `LICENSE` file containing the official MIT License text and replace this section with the appropriate copyright holder and year.

---

**Dictionary Attack Lab**  
An educational JavaFX implementation for studying authentication attack mechanics in a controlled environment.
