/**
 * Created by Дмитрий on 17.12.2014.
 */

function showMessage(message){
    //тут надо обработать сообщение и правильно его вывести.
    //не забыть отновить в диалогах

}

function showHistory(messages){
    //сделано, прикрутить socket
}

function showConversations(){

}

var socket = WebSocket("ws://...")

socket.onopen = function() {
    alert("Соединение установлено");
}

socket.onclose = function(event){
    if (event.wasClean) {
        alert("Соединение закрыто успешно");
    } else {
        alert("Обрыв соединения");
    }
    alert('Код: ' + event.code + ' причина: ' + event.reason);
}

socket.onmessage = function(event){
    var data = JSON.parse(event.data);
    if (data.fromUserId) {
        showMessage(data);
    } else {
        if (data.messages) {
            showHistory()
        } else {
            if (data.conversations) {
                //Ask what it is.
            }
        }
    }
}