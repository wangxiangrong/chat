/**
 * Created by Дмитрий on 16.12.2014.
 */
function sendMessage() {
    var mesText =  document.getElementById('message').value;
    if (mesText == ''){
        return;
    }
    var messageField = document.getElementById("message");
    var message = {
        "fromUserId" : document.getElementById("userId").innerHTML,
        "toUserId" : 0,//надо забрать id текущего диалога.
        "message" : mesText
        //тут еще несколько непонятных вещей
    }
    var list = document.getElementById("messageList");
    var mes = document.createElement('div');
    var element = document.createElement('div')
    var fromMesContainer = document.createElement('div');
    mes.innerHTML = mesText;
    messageField.value = '';
    fromMesContainer.className = "outgoingMessageText";
    fromMesContainer.appendChild(mes);
    element.appendChild(fromMesContainer);
    list.appendChild(element);
    var container = document.getElementById('dialog');
    container.scrollTop = container.scrollHeight;

    //меняем последнее сообщение в списке диалогов
    var dialogList = document.getElementById('interlocutorsList');
    for (var i = 0; i < dialogList.childNodes.length; i++) {
        curInterlocutor = dialogList.childNodes[i];
        if (curInterlocutor.childNodes[1].innerHTML == document.getElementById('currentInterlocutor').innerHTML){
            curInterlocutor.childNodes[0].innerHTML = 'me';
            curInterlocutor.childNodes[2].innerHTML = 'You: ' + mesText;
        }
    }
    socket.send(JSON.stringify(message));
}

