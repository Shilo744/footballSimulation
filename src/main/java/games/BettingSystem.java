package games;

import com.footballsimulation.controllers.GeneralController;
import com.footballsimulation.entities.Bet;
import com.footballsimulation.entities.User;

import java.util.ArrayList;
import java.util.List;

public class BettingSystem {
    private List<User> users;

    public BettingSystem() {
        betsHandler();
        this.users = GeneralController.dbUtils.getAllUsers();

    }
    public void betsHandler(){
        new Thread(()->{
            while (true){
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
                for (User user:users) {
                ArrayList<Bet>newBets=new ArrayList<>();
                while (!user.getGoingBets().isEmpty()){
                    Bet currentBetCheck=user.getGoingBets().remove(0);
                    if(!currentBetCheck.isOver()){
                        newBets.add(currentBetCheck);
                    }else {
                        user.getOverBets().add(currentBetCheck);
                        if(currentBetCheck.isWin()){
                            int reward=currentBetCheck.reward();
                            user.addMoney(reward);
                        }
                    }
                }
                user.setGoingBets(newBets);
            }
            for (User user:users) {
                System.out.println(user.getUsername()+": balance: "+user.getBalance());
                System.out.println(user.getOverBets()+" - "+user.getGoingBets());
            }
            }
        }).start();
    }
}
