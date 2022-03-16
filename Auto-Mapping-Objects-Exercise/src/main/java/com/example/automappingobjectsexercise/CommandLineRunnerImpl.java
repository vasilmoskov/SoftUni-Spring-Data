package com.example.automappingobjectsexercise;

import com.example.automappingobjectsexercise.models.dto.GameAddDto;
import com.example.automappingobjectsexercise.models.dto.GameEditDto;
import com.example.automappingobjectsexercise.models.dto.UserLoginDao;
import com.example.automappingobjectsexercise.models.dto.UserRegisterDto;
import com.example.automappingobjectsexercise.services.GameService;
import com.example.automappingobjectsexercise.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final BufferedReader bufferedReader;
    private final UserService userService;
    private final GameService gameService;

    public CommandLineRunnerImpl(UserService userService, GameService gameService) {
        this.gameService = gameService;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {

        while (true) {
            System.out.println("Enter your commands:");
            String[] commands = bufferedReader.readLine().split("\\|");

            switch (commands[0]) {
                case "RegisterUser":
                    userService.register(new UserRegisterDto(commands[1],
                            commands[2], commands[3], commands[4]));
                    break;
                case "LoginUser":
                    userService.login(new UserLoginDao(commands[1], commands[2]));
                    break;
                case "Logout":
                    userService.logout();
                    break;
                case "AddGame":
                    gameService.add(new GameAddDto(commands[1], commands[2], commands[3],
                            Double.parseDouble(commands[4]), new BigDecimal(commands[5]),
                            commands[6], commands[7]));
                    break;
                case "EditGame":
                    gameService.edit(new GameEditDto(Long.parseLong(commands[1]), new BigDecimal(commands[2]),
                            Double.parseDouble(commands[3])));
            }
        }
    }
}
