const express = require('express');
const mysql = require('mysql2');
const cors = require('cors');
const app = express();
app.use(cors());
app.use(express.json());

const db = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '',
    database: 'amarine'
});

db.connect((err) => {
    if (err) {
        console.error('Error connecting to the database:', err.stack);
        return;
    }
    console.log('Connected to the database!');
});

// LOGIKA BUAT REGISTER OM
app.post('/akun', (req, res) => {
    const { email, password, role, nama } = req.body;

    // Validasi input
    if (!email || !password || !nama) {
        return res.status(400).json({ error: 'Email, password, dan nama harus diisi!' });
    }

    // Query untuk memasukkan email, password, dan role='Nelayan' ke tabel akun
    const insertAkunQuery = 'INSERT INTO akun (email, password, role) VALUES (?, ?, "Nelayan")';
    db.query(insertAkunQuery, [email, password], (err, akunResult) => {
        if (err) {
            console.error('Error inserting data into akun:', err.stack);
            return res.status(500).json({ error: 'Failed to insert data into akun' });
        }

        const akunId = akunResult.insertId;
        const insertNelayanQuery = 'INSERT INTO nelayan (id_akun, nama) VALUES (?, ?)';
        db.query(insertNelayanQuery, [akunId, nama], (err, nelayanResult) => {
            if (err) {
                console.error('Error inserting data into nelayan:', err.stack);
                db.query('DELETE FROM akun WHERE id = ?', [akunId]);
                return res.status(500).json({ error: 'Failed to insert data into nelayan' });
            }

            res.status(200).json({
                message: 'Registrasi berhasil',
                data: {
                    email: email,
                    nama: nama
                }
            });
        });
    });
});

// LOGIKA BUAT LOGIN
app.post('/masuk', (req, res) => {
    const { email, password } = req.body;

    // Validasi input
    if (!email || !password) {
        return res.status(400).json({
            error: 'Email dan password harus diisi!'
        });
    }

    const loginQuery = `
        SELECT a.*, n.nama, n.id as nelayan_id
        FROM akun a
        LEFT JOIN nelayan n ON a.id = n.id_akun
        WHERE a.email = ? AND a.password = ?
    `;

    db.query(loginQuery, [email, password], (err, results) => {
        if (err) {
            console.error('Error saat login:', err.stack);
            return res.status(500).json({
                error: 'Terjadi kesalahan saat login'
            });
        }

        if (results.length === 0) {
            return res.status(401).json({
                error: 'Email atau password salah!'
            });
        }

        const user = results[0];

        res.status(200).json({
            message: 'Login berhasil',
            data: {
                id: user.id,
                email: user.email,
                role: user.role,
                nama: user.nama,
                nelayan_id: user.nelayan_id
            }
        });
    });
});

// Jalankan server
//const port = 3000;
//app.listen(port, () => {
//    console.log(`Server berjalan di http://localhost:${port}`);
//});

app.listen(3000, '0.0.0.0', () => {
  console.log('Server running on port 3000');
});