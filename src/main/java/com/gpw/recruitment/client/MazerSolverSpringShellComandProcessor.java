package com.gpw.recruitment.client;

import com.gpw.recruitment.service.MazeSolverCLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;


@ShellComponent
public class MazerSolverSpringShellComandProcessor {
    @Autowired
    MazeSolverCLService mazeSolverCLService;

    @ShellMethod("calling with parameter Task 2")
    public String task1(@ShellOption(defaultValue="src/main/resources/task1.txt") String param)  { //--Any parametr (Dowolny parametr zgodnie z wymagamiami Zadanie 2)
        if(param.equals("src/main/resources/task1.txt")){
            return mazeSolverCLService.processFile("src/main/resources/task1.txt");
        }
        return mazeSolverCLService.processBinaryFile("src/main/resources/task1.txt");
    }
}
