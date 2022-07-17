package me.tim.checks;

import me.tim.checks.impl.combat.NoSwingA;
import me.tim.checks.impl.combat.killaura.KillAuraA;
import me.tim.checks.impl.combat.killaura.KillAuraB;
import me.tim.checks.impl.combat.killaura.KillAuraC;
import me.tim.checks.impl.movement.NoFall;
import me.tim.checks.impl.movement.fly.FlyA;

import java.util.ArrayList;

public class CheckManager {
    private ArrayList<Check> checks;

    public CheckManager()
    {
        this.checks = new ArrayList<>();
        //KillAura
        this.checks.add(new KillAuraA());
        this.checks.add(new KillAuraB());
        this.checks.add(new KillAuraC());

        //Fly
        this.checks.add(new FlyA());

        //Other
        this.checks.add(new NoFall());
        this.checks.add(new NoSwingA());
    }

    public ArrayList<Check> getChecks() {
        return checks;
    }

    public Check getCheck(String name)
    {
        for (Check check : this.checks) {
            if (check.getName().equalsIgnoreCase(name))
            {
                return check;
            }
        }
        return null;
    }

    public Check getCheck(Check check)
    {
        for (Check check_ : this.checks) {
            if (check_ == check)
            {
                return check_;
            }
        }
        return null;
    }
}
