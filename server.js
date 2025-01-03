const express = require('express');
const nodemailer = require('nodemailer');
const bodyParser = require('body-parser');
const cors = require('cors');

const app = express();
app.use(bodyParser.json());
app.use(cors());

app.post('/send-email', async (req, res) => {
    const { email, subject, message } = req.body;

    const transporter = nodemailer.createTransport({
        service: 'gmail',
        auth: {
            user: 'your-email@gmail.com',
            pass: 'your-app-password',
        },
    });

    try {
        await transporter.sendMail({
            from: 'your-email@gmail.com',
            to: email,
            subject: subject || 'Confirmation Email',
            text: message || 'Thank you for signing up!',
        });
        res.status(200).send('Email sent successfully');
    } catch (error) {
        console.error(error);
        res.status(500).send('Error sending email');
    }
});

const PORT = 3000;
app.listen(PORT, () => {
    console.log(`Server running on port ${PORT}`);
});
