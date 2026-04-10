/**
 * Mock Login Server - Ejercicio 1
 * Simula el endpoint POST /auth/login de fakestoreapi.com
 * Usado como alternativa mientras fakestoreapi.com presenta error 526 (SSL Cloudflare)
 */

const http = require('http');

const PORT = 3001;

// Usuarios validos (mismos del CSV login_users.csv)
const VALID_USERS = {
  'donero':    'ewedon',
  'kevinryan': 'kev02937@',
  'johnd':     'm38rmF$',
  'derek':     'jklg*_56',
  'mor_2314':  '83r5^_'
};

function generateToken(username) {
  const payload = Buffer.from(JSON.stringify({ user: username, iat: Date.now() })).toString('base64');
  return `eyJhbGciOiJIUzI1NiJ9.${payload}.mock_signature`;
}

const server = http.createServer((req, res) => {
  // CORS headers
  res.setHeader('Access-Control-Allow-Origin', '*');
  res.setHeader('Content-Type', 'application/json');

  if (req.method === 'POST' && req.url === '/auth/login') {
    let body = '';
    req.on('data', chunk => { body += chunk.toString(); });
    req.on('end', () => {
      try {
        const { username, password } = JSON.parse(body);

        if (VALID_USERS[username] && VALID_USERS[username] === password) {
          res.writeHead(200);
          res.end(JSON.stringify({ token: generateToken(username) }));
          console.log(`[OK]  ${username} - 200`);
        } else {
          res.writeHead(401);
          res.end(JSON.stringify({ message: 'username or password is incorrect' }));
          console.log(`[ERR] ${username} - 401`);
        }
      } catch (e) {
        res.writeHead(400);
        res.end(JSON.stringify({ message: 'invalid request body' }));
      }
    });
  } else {
    res.writeHead(404);
    res.end(JSON.stringify({ message: 'not found' }));
  }
});

server.listen(PORT, () => {
  console.log('='.repeat(50));
  console.log(` Mock Login Server corriendo en http://localhost:${PORT}`);
  console.log(` Endpoint: POST http://localhost:${PORT}/auth/login`);
  console.log(' Presiona Ctrl+C para detener');
  console.log('='.repeat(50));
});
