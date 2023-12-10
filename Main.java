import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

    boolean flag = true;
    Scanner scanner = new Scanner(System.in);

    while (flag) {
        System.out.println("\t\tМеню\n 1 - Ввести данные\n 2 - Просмотр данных\n 3 - выход");
        String input = scanner.nextLine();
        switch (input) {
            case "1":
                Add();
                break;
            case "2":
                Info();
                break;
            case "3":
                flag = false;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + input);
        }
    }
}

    private static void Info() {
        File folder = new File("File_txt\\");
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));

        if(files!=null){
            for(File file :files){

                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String line;
                    System.out.println("Содержимое файла " + file.getName() + ":");
                    while ((line = reader.readLine()) != null){
                        System.out.println(line);
                    }
                    reader.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            }
        }
    }

    private static void Add() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите данные в формате: Фамилия Имя Отчество датарождения номертелефона пол");
        System.out.println("Пример: \nВасиленко Алексей Юрьевич 1980.10.06 89104536453 m");
        String input = scanner.nextLine();
        String[] data = input.split(" ");

        String directoryPath = "File_txt";
        File directory = new File(directoryPath );
        if (!directory.exists()) {
            directory.mkdirs();
        }

        if (data.length != 6) {
            System.out.println("Ошибка: введено неверное количество данных");
            return;
        }

        String lastName = data[0];
        String firstName = data[1];
        String middleName = data[2];
        String birthDate = data[3];
        String phoneNumber = data[4];
        String gender = data[5];


        try {
            if (!birthDate.matches("\\d{4}.\\d{2}.\\d{2}")) {
                throw new InvalidDataFormatException("Неверный формат даты рождения");
            }

            Long.parseLong(phoneNumber);

            if (!gender.equals("f") && !gender.equals("m")) {
                throw new InvalidDataFormatException("Неверный формат пола");
            }
            if (phoneNumber.length () <6 ) {
                throw new InvalidDataFormatException("Ошибка: Неверный формат номера телефона");
            }

            String fileName = lastName + ".txt";
            File file = new File(directory,fileName);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));

            writer.write(lastName + " " + firstName + " " + middleName + " " + birthDate + " " + phoneNumber + " " + gender + "\n");
            writer.close();
            System.out.println("Данные успешно добавленны в "+ fileName);
        } catch (InvalidDataFormatException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: Неверный формат номера телефона");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class InvalidDataFormatException extends Throwable {
        public InvalidDataFormatException(String message) {
            super(message);
        }
    }
}


