package games;

import com.footballsimulation.controllers.GeneralController;
import com.footballsimulation.entities.Bet;
import com.footballsimulation.entities.User;

import java.util.ArrayList;
import java.util.List;

public class BettingSystem {
    private List<User> users;
    private static ArrayList<Bet>goingBets;
    private static ArrayList<Bet>overBets;

    public BettingSystem() {
        goingBets=new ArrayList<>();
        overBets=new ArrayList<>();
        betsHandler();
    }

    public static ArrayList<Bet> getGoingBets() {
        return goingBets;
    }

    public static ArrayList<Bet> getOverBets() {
        return overBets;
    }

    public void betsHandler(){
        new Thread(()->{
            while (true){
                try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
                System.out.println(goingBets.size()+" going bets before: "+goingBets);

                this.users = GeneralController.dbUtils.getAllUsers();

                ArrayList<Bet>newBets=new ArrayList<>();
                while (!goingBets.isEmpty()){
                    Bet currentBetCheck=goingBets.remove(0);
                    if(currentBetCheck.isOver()){
                        overBets.add(currentBetCheck);
                        if(currentBetCheck.isWin()){
                            int reward=currentBetCheck.getReward();
                            System.out.println(reward);
                            GeneralController.persist.updateBalanceById(currentBetCheck.getUserId(),reward);
                        }
                    }else {
                        newBets.add(currentBetCheck);
                    }
            }
                goingBets=newBets;

                System.out.println(goingBets.size()+" going bets after: "+goingBets);
            }
        }).start();
    }
    public boolean makeBet(int id,Game game,int choice,int amount){
        if(choice>=Game.HOME_WIN && choice<=Game.TIE){
            GeneralController.persist.updateBalanceById(id,-amount);
            goingBets.add(new Bet(id,game,choice,amount));

            return true;
        }
        return false;
    }
}
