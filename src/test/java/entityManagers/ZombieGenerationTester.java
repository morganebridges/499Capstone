package entityManagers;
import com.zombie.entityManagers.ZombieGenerationManager;
import com.zombie.models.User;
import com.zombie.models.Zombie;
import junit.framework.*;
import org.junit.Test;

import java.util.Scanner;

class ZombieGenerationTester extends TestCase{
    public static void main(Object lockObject){
        Scanner input = new Scanner(System.in);
        String token;
        while((token = input.next()) != null){
        if(token.equals("q"))
            return;
            System.out.println();

        }
    }
    @Test
    public void testGenerationEquation(){
        ZombieGenerationManager testguy = new ZombieGenerationManager();
        User me = new User("Morgan");
        me.setLatitude(45.0);
        me.setLongitude(-95.2);
        Zombie testZom = testguy.genRandomZombie(me);
        System.out.println("breakpoint");
    }
}