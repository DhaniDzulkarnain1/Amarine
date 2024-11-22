const express = require('express');
const bodyParser = require('body-parser');
const mysql = require('mysql2');
const cors = require('cors');

const app = express();
const port = 3000;

// Middleware
app.use(cors());
app.use(bodyParser.json());

// Koneksi ke MySQL
const db = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '',
    database: 'amarine'
});

db.connect((err) => {
    if (err) {
        console.error('Koneksi ke database gagal:', err);
    } else {
        console.log('Koneksi ke database berhasil!');
    }
});


//Endpoint untuk registrasi akun
app.post('/register', (req, res) => {
    const { name, email, password } = req.body;

    if (!name || !email || !password) {
        return res.status(400).json({ message: 'Semua field wajib diisi!' });
    }

    console.log(`Nama: ${name}, Email: ${email}, Password: ${password}`);
    res.status(201).json({ message: 'Pendaftaran berhasil!' });
});
//Endpoint untuk login
// Endpoint untuk menyimpan data
app.post('/insert', (req, res) => {
    const { nama, umur } = req.body;

    if (!nama || !umur) {
        return res.status(400).send({ message: 'Data tidak lengkap' });
    }

    const query = 'INSERT INTO users (nama, umur) VALUES (?, ?)';
    db.query(query, [nama, umur], (err, result) => {
        if (err) {
            return res.status(500).send({ message: 'Gagal menyimpan data', error: err });
        }
        res.status(200).send({ message: 'Data berhasil disimpan', data: result });
    });
});

// Jalankan server
app.listen(port, () => {
    console.log(`Server berjalan di http://localhost:${port}`);
});