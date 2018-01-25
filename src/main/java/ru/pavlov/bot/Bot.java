package ru.pavlov.bot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Bot extends TelegramLongPollingBot {


    public void onUpdateReceived(Update update) {
        List<String> lines = null;

        try {
            lines = Files.readAllLines(Paths.get("/Users/antonpavlov/IdeaProjects/BotTelegram/src/main/resources/file.txt"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Set<String> set = new HashSet<>(lines);
        lines = new ArrayList<>(set);

        int randomMessage = (int) (Math.random() * lines.size());
        String textMessage = lines.get(randomMessage);

        Message message = update.getMessage();
        if (message != null && message.hasText()) {

            if (message.getText().toLowerCase().equals("жги")) {
                if (textMessage.length() < 3) {
                    onUpdateReceived(update);
                }
                sendMsg(message, "Жгу: " + textMessage);
            } else {
//                sendMsg(message, "Я не знаю этой команды " + message.getText());
            }
        }
    }

    private void sendMsg(Message message, String MessageText) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(MessageText);

        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String getBotUsername() {
        return "";
    }

    public String getBotToken() {
        return "";
    }
}
