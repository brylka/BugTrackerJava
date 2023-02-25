document.querySelector("#formPassword").addEventListener('submit', function (e) {
    e.preventDefault();

    const minLength = 8;
    const hasLowerCase = /[a-z]/;
    const hasUpperCase = /[A-Z]/;
    const hasNumbers = /[0-9]/;

    const password = document.querySelector('#password').value;
    const passwordConfirm = document.querySelector('#passwordConfirm').value;
    if (password === '') {
        document.querySelector("#password").style.border = "2px solid red";
        document.querySelector(".alert").style.display = "block";
        document.querySelector(".alert").innerText = passwordBlank;
        setTimeout(function () {
            document.querySelector(".alert").style.display = "none";
        }, 5000)
        return;
    }

    if (password !== passwordConfirm) {
        document.querySelector("#password").style.border = "2px solid red";
        document.querySelector("#passwordConfirm").style.border = "2px solid red";
        document.querySelector(".alert").style.display = "block";
        document.querySelector(".alert").innerText = passwordNotConfirmed;
        setTimeout(function () {
            document.querySelector(".alert").style.display = "none";
        }, 5000)
        return;
    }

    if (password.length < minLength) {
        document.querySelector("#password").style.border = "2px solid red";
        document.querySelector("#passwordConfirm").style.border = "2px solid red";
        document.querySelector(".alert").style.display = "block";
        document.querySelector(".alert").innerText = passwordMinLength;
        setTimeout(function () {
            document.querySelector(".alert").style.display = "none";
        }, 5000)
        return;
    }

    if (!hasLowerCase.test(password)) {
        document.querySelector("#password").style.border = "2px solid red";
        document.querySelector("#passwordConfirm").style.border = "2px solid red";
        document.querySelector(".alert").style.display = "block";
        document.querySelector(".alert").innerText = passwordLowerCase;
        setTimeout(function () {
            document.querySelector(".alert").style.display = "none";
        }, 5000)
        return;
    }

    if (!hasUpperCase.test(password)) {
        document.querySelector("#password").style.border = "2px solid red";
        document.querySelector("#passwordConfirm").style.border = "2px solid red";
        document.querySelector(".alert").style.display = "block";
        document.querySelector(".alert").innerText = passwordUpperCase;
        setTimeout(function () {
            document.querySelector(".alert").style.display = "none";
        }, 5000)
        return;
    }

    if (!hasNumbers.test(password)) {
        document.querySelector("#password").style.border = "2px solid red";
        document.querySelector("#passwordConfirm").style.border = "2px solid red";
        document.querySelector(".alert").style.display = "block";
        document.querySelector(".alert").innerText = passwordNumber;
        setTimeout(function () {
            document.querySelector(".alert").style.display = "none";
        }, 5000)
        return;
    }
    this.submit();
});