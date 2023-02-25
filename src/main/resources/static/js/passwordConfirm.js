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
        document.querySelector(".alert").innerText = "Pole hasło nie może być puste!";
        setTimeout(function () {
            document.querySelector(".alert").style.display = "none";
        }, 5000)
        return;
    }

    if (password !== passwordConfirm) {
        document.querySelector("#password").style.border = "2px solid red";
        document.querySelector("#passwordConfirm").style.border = "2px solid red";
        document.querySelector(".alert").style.display = "block";
        document.querySelector(".alert").innerText = "Hasła nie są takie same!";
        setTimeout(function () {
            document.querySelector(".alert").style.display = "none";
        }, 5000)
        return;
    }

    if (password.length < minLength) {
        document.querySelector("#password").style.border = "2px solid red";
        document.querySelector("#passwordConfirm").style.border = "2px solid red";
        document.querySelector(".alert").style.display = "block";
        document.querySelector(".alert").innerText = "Hasło musi zawierać co najmniej 8 znaków!";
        setTimeout(function () {
            document.querySelector(".alert").style.display = "none";
        }, 5000)
        return;
    }

    if (!hasLowerCase.test(password)) {
        document.querySelector("#password").style.border = "2px solid red";
        document.querySelector("#passwordConfirm").style.border = "2px solid red";
        document.querySelector(".alert").style.display = "block";
        document.querySelector(".alert").innerText = "Hasło musi zawierać małe litery!";
        setTimeout(function () {
            document.querySelector(".alert").style.display = "none";
        }, 5000)
        return;
    }

    if (!hasUpperCase.test(password)) {
        document.querySelector("#password").style.border = "2px solid red";
        document.querySelector("#passwordConfirm").style.border = "2px solid red";
        document.querySelector(".alert").style.display = "block";
        document.querySelector(".alert").innerText = "Hasło musi zawierać duże litery!";
        setTimeout(function () {
            document.querySelector(".alert").style.display = "none";
        }, 5000)
        return;
    }

    if (!hasNumbers.test(password)) {
        document.querySelector("#password").style.border = "2px solid red";
        document.querySelector("#passwordConfirm").style.border = "2px solid red";
        document.querySelector(".alert").style.display = "block";
        document.querySelector(".alert").innerText = "Hasło musi zawierać cyfry!";
        setTimeout(function () {
            document.querySelector(".alert").style.display = "none";
        }, 5000)
        return;
    }
    this.submit();
});