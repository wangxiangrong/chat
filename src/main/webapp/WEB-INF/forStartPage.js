/**
 * Created by Дмитрий on 16.12.2014.
 */

function showStartPage() {
    var page = "<div class='header'> <div class='iconContainer'><div class='icon'></div><div class='appName'>Chat</div></div></div>";
    var pp = "<form class='loginForm' id='startForm'>" +
    "<div id='signInHeader'>Вход</div>" +
    "<div class='box1'>" +
    "<label for='loginField' id='loginLabel'>Введите логин:</label>" +
    "<input type='text' class='login' id='loginField'>" +
    "</div>" +
    "<button type='button' class='button' id='signIn' onclick='toDialogsPage()'>SIGN IN</button>" +
    "</form>";
    document.body.innerHTML = page + pp;
}