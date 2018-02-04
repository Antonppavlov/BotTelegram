package ru.pavlov.bot;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    public static final String CONFIGURATION_BOT_FILE = "./config/bot/bot.properties";
    public static final String CONFIGURATION_DB_FILE = "./config/database/database.properties";

    public static String BOT_NAME;
    public static String BOT_TOKEN;

    public static String BOT_URL;
    public static String BOT_USER;
    public static String BOT_PWD;


    public static void load() {

        Properties botSetting = new Properties();
        try (InputStream inputStream = new FileInputStream(new File(CONFIGURATION_BOT_FILE))) {
            botSetting.load(inputStream);
            inputStream.close();
            System.out.println("Конфиг бота загрузился!");
        } catch (Exception e) {
            System.err.println("Конфиг бота не загрузился!!!\n" + e.getMessage());
        }

        Properties databaseSetting = new Properties();
        try (InputStream inputStream = new FileInputStream(new File(CONFIGURATION_DB_FILE))) {
            databaseSetting.load(inputStream);
            inputStream.close();
            System.out.println("Конфиг database загрузился!");
        } catch (Exception e) {
            System.err.println("Конфиг database не загрузился!!!\n" + e.getMessage());
        }

        BOT_NAME = botSetting.getProperty("bot_name");
        BOT_TOKEN = botSetting.getProperty("bot_token");

        BOT_URL = databaseSetting.getProperty("bot_url");
        BOT_USER = databaseSetting.getProperty("bot_user");
        BOT_PWD = databaseSetting.getProperty("bot_pwd");

    }

}
