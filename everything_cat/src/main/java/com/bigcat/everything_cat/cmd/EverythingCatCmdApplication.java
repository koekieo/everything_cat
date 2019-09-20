package com.bigcat.everything_cat.cmd;

import com.bigcat.everything_cat.config.EverythingConfig;
import com.bigcat.everything_cat.core.EveryThingManager;
import com.bigcat.everything_cat.core.model.Condition;

import java.util.Scanner;

/**
 * @auther koekie
 * @date 2019/3/26 18:46
 * @function
 */

public class EverythingCatCmdApplication {
    public static void main(String[] args) {
        //1. EverthingManager
        EveryThingManager manager = EveryThingManager.getInstance();

        //2. Scanner
        Scanner scanner = new Scanner(System.in);

        //3. 用户交互输出
        System.out.println("欢迎使用,everything_cat");
        while (true) {
            System.out.print(">>");
            String line = scanner.nextLine();
            switch (line) {
                case "help" : {
                    manager.help();
                    break;
                }
                case "quit" : {
                    manager.quit();
                    break;
                }
                case "index" : {
                    manager.buildIndex();
                    break;
                }
                default:{
                    if (line.startsWith("search")) {
                        //解析参数
                        String[] segments = line.split(" ");


                        if (segments.length >= 2) {
                            Condition condition = new Condition();
                            String name = segments[1];
                            condition.setName(name);
                            if (segments.length >= 3) {
                                String type = segments[2];
                                condition.setFileType(type.toUpperCase());
                            }
                            manager.search(condition)
                                    .forEach(thing -> {
                                        System.out.println(thing.getPath());
                                    });
                        } else {
                            manager.help();
                        }
                    } else {
                        manager.help();
                    }
                }

            }
        }
        }


    }


