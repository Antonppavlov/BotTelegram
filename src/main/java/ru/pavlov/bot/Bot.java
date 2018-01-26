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

    static int countMessage = 0;

    public void onUpdateReceived(Update update) {

        countMessage = countMessage + 1;
        System.out.println(countMessage);
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            if (countMessage >= 10) {
                sendMsg(message, "Эй кожаный: " + getMessageRobot());
                countMessage = 0;
            } else if (message.getText().toLowerCase().equals("жги")) {
                sendMsg(message, "Жгу: " + getMessageJoke());
            } else if (message.getText().toLowerCase().equals("лох")) {
                sendMsg(message, "пиииииииииидр!");
            } else if (message.getText().toLowerCase().contains("жы") || message.getText().toLowerCase().contains("шы")) {
                sendMsg(message, "ну епта! 'Жи' 'Ши' пиши с буковой 'И'");
            } else if (message.getText().toLowerCase().contains("are you")) {
                sendMsg(message, "ahueli tam?");
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


    private String getMessageRobot() {
        return getMessage("/Users/antonpavlov/IdeaProjects/BotTelegram/src/main/resources/robot.txt");
    }


    private String getMessageJoke() {
        return getMessage("/Users/antonpavlov/IdeaProjects/BotTelegram/src/main/resources/joke.txt");
    }

    private String getMessage(String pathToFile) {
        List<String> lines = null;

        try {
            lines = Files.readAllLines(Paths.get(pathToFile), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Set<String> set = new HashSet<>(lines);
        lines = new ArrayList<>(set);

        int randomMessage = (int) (Math.random() * lines.size());
        String textMessage = lines.get(randomMessage);

        if (textMessage.length() < 2) {
            textMessage = getMessage(pathToFile);
        }


        return textMessage;
    }
}
