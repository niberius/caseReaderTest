package a1qa;

import a1qa.utils.CaseReader;

import java.io.File;


public class Main {

    public static void main(String[] args){
        String path = "./src/main/resources/all_pairs.txt";
        int quantity = 20;

        //из cmd: 2 arg
        if(args.length == 2){
            path = args[0];
            quantity = Integer.valueOf(args[1]);
        }else if(args.length == 1){
            path = args[0];
        }

        CaseReader cr = new CaseReader();
        cr.selectCases(new File(path), quantity);
    }
}
