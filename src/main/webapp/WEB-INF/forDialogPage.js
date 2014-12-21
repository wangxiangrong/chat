/**
 * Created by Дмитрий on 16.12.2014.
 */
function showHistory(messages) {
    //need to show history
    var list = document.getElementById("messageList");
    var mme = {
        "message": "Привет",
        "fromUserId": "Dmitri"
    };
    var mme1 = {
        "message": "Привет",
        "fromUserId": "Cool"
    };
    messages = [mme, mme1, mme, mme, mme, mme, mme, mme, mme, mme, mme, mme, mme, mme, mme, mme, mme, mme, mme, mme, mme, mme, mme];
    var container = document.getElementById('dialog');
    list.innerHTML = '';
    list.className = 'messageList';
    for (var i = 0; i < messages.length; i++) {
        var element = document.createElement('div');
        var mes = document.createElement('div');
        mes.innerHTML = messages[i].message;
        mes.className = "messageText";
        if (messages[i].fromUserId == document.getElementById('userId').innerHTML) {
            var fromMesContainer = document.createElement('div');
            fromMesContainer.className = "outgoingMessageText";
            fromMesContainer.appendChild(mes);
            element.appendChild(fromMesContainer);
        } else {
            var toMesContainer = document.createElement('div');
            toMesContainer.className = "incomingMessageText";
            toMesContainer.appendChild(mes);
            element.appendChild(toMesContainer);
        }
        list.appendChild(element);
    }
    container.appendChild(list);
    container.scrollTop = container.scrollHeight;
}

function clearAll() {
    var interlocutorList = document.getElementById('interlocutorsList').childNodes;
    for (var i = 0; i < interlocutorList.length; i++) {
        interlocutorList[i].style.background = 'none';
    }
}

function showInterlocutorHistory() {

}

function showConversations() {
    //запрос через socket
    function newMes(from, mes) {
        return {
            "message": mes,
            "fromUserId": from
        }
    }

    var container = document.getElementById('dialogsList');
    var list = document.createElement('div');
    list.id = "interlocutorsList";
    var conversations = [new newMes('Cool', 'hi'), new newMes('Dmirty', 'hiaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa'), new newMes('Cool1', 'hello'), new newMes('Cool2', 'hi')]
    for (var i = 0; i < conversations.length; i++) {
        var interlocutor = document.createElement('div');
        var currentMes = conversations[i];
        var whom = document.createElement('div');
        var mesText = document.createElement('div');
        whom.className = 'interlocutorName';
        mesText.className = 'mesText';
        interlocutor.className = 'interlocutorElement';
        whom.innerHTML = currentMes.fromUserId;
        var flag = document.createElement('div');
        flag.style.display = 'none';
        if (currentMes.fromUserId == document.getElementById('userId').innerHTML) {
            mesText.innerHTML = 'You: ' + currentMes.message;
            flag.innerHTML = 'me';
        } else {
            mesText.innerHTML = currentMes.message;
            flag.innerHTML = 'toMe';
        }
        interlocutor.appendChild(flag);
        interlocutor.appendChild(whom);
        interlocutor.appendChild(mesText);
        interlocutor.onclick = function () {
            clearAll();
            var container = document.getElementById('messageList');
            var mesList = document.createElement('div');
            document.getElementById('messageList').innerHTML = '';
            var message = document.createElement('div');
            if (this.childNodes[0].innerHTML == 'me') {
                message.className = 'outgoingMessageText';
                message.innerHTML = this.childNodes[2].innerHTML;
            } else {
                message.className = 'incomingMessageText';
                message.innerHTML = this.childNodes[2].innerHTML;
            }
            mesList.appendChild(message);
            container.appendChild(mesList);
            this.style.background = 'rgb(200, 220, 256)';
            document.getElementById('currentInterlocutor').innerHTML = this.childNodes[1].innerHTML;
        };
        list.appendChild(interlocutor);
    }
    container.appendChild(list);
    //выбираем по умолчанию первый

    clearAll();
    var container = document.getElementById('messageList');
    var mesList = document.createElement('div');
    document.getElementById('messageList').innerHTML = '';
    var message = document.createElement('div');
    if (list.childNodes[0].childNodes[0].innerHTML == 'me') {
        message.className = 'outgoingMessageText';
        message.innerHTML = list.childNodes[0].childNodes[2].innerHTML;
    } else {
        message.className = 'incomingMessageText';
        message.innerHTML = list.childNodes[0].childNodes[2].innerHTML;
    }
    mesList.appendChild(message);
    container.appendChild(mesList);
    list.childNodes[0].style.background = 'rgb(200, 220, 256)';
    document.getElementById('currentInterlocutor').innerHTML = list.childNodes[0].childNodes[1].innerHTML;

    //
}

function toDialogsPage() {
    var user = document.getElementById("loginField").value;
    var page0 = "<p style='display: none;' id='userId'>" + user + "</p>";
    var socketConnect = "<script src='socketScripts.js'></script>"
    var page1 = "<div class='header'><div class='iconContainer'><div class='icon'></div><div class='appName'>Chat</div></div></div>"
    var page2 = "<div class='workPlace'><div class='dialogsList' id='dialogsList'></div>";
    var page3 = "<div class='dialog'><div class='dialogHeader'><div class='interlocutor' id='currentInterlocutor'>ОООООчень длинное имяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяяя</div>";
    var page4 = "<button class='button historyButton' onclick='showHistory()'>История</button></div><div class='dialogShow' id='dialog'><div id='messageList'></div></div>";
    var page5 = "<div class='dialogOptions'><textarea type='text' id='message' placeholder='Введите ваше сообщение' ></textarea><input type='submit' class='button' id='sendMessage' onclick='sendMessage()'></div></div></div>";
    document.body.innerHTML = page0 + socketConnect + page1 + page2 + page3 + page4 + page5;
    showConversations();
}

