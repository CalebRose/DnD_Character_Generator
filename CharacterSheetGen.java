import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class CharacterSheetGen
{
    public static void main(String[] args) throws Exception
    {
        /*
        Variables Needed
        Level
        Alignment
        Background?

        Attributes
        Strength
        Dexterity
        Constitution
        Intelligence
        Wisdom
        Charisma

        Health
        Armor Class
        Initiative

        Skills
        Acrobatics
        Animal Handling
        Arcana
        Athletics

        */
        GenerateCharacter();

    }

    public static void GenerateCharacter() throws Exception
    {
        // Variables
        String Name = "";
        String Race = "";
        String Class = "";
        int Level = 0;
        int exp = 0;
        String Alignment = "";
        boolean RandAbility = false;
        int[] AbilityScore = new int[6];
        String[] Attributes = {"Strength", "Dexterity", "Constitution", "Intelligence", "Wisdom", "Charisma"};

        // === Class Object ===
        CharacterSheetGen Character = new CharacterSheetGen();
        // === Name Input ===
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Name = reader.readLine();
        System.out.println("How would you like to generate your character?");
        System.out.println("(C)ustom Design");
        System.out.println("(R)andomized");

        // === Generate Variables ===
        String choice = reader.readLine();
        if(choice.equalsIgnoreCase("R"))
        {
            RandAbility = true;
            Race = Character.RaceGenerator(Race);
            Class = Character.ClassGenerator(Class);
            Alignment = Character.AlignmentGenerator(Alignment);
            Character.AbilityGenerator(RandAbility, AbilityScore, Attributes, Class);
        }
        else if(choice.equalsIgnoreCase("C"))
        {
            RandAbility = false;
            System.out.println("Input Race: ");
            System.out.println("Options: Human, Elf, Half Elf, Dwarf, Orc, Gnome, Tiss, Lizard, Norn, Charr");
            Race = reader.readLine(); // Input Race
            System.out.println("Input Class: ");
            System.out.println("Options: Barbarian, Bard, Druid, Engineer, Monk, Paladin, Ranger, Rogue, Sorcerer, Warlock, and Wizard");
            Class = reader.readLine(); // Input Class
            System.out.println("Input Alignment: ");
            System.out.println("Options: Lawful Good, Lawful Neutral, Lawful Evil, Neutral Good, Neutral Evil, Chaotic Good, Chaotic Neutral, Chaotic Evil");
            Alignment = reader.readLine(); // Input Alignment
            Character.AbilityGenerator(RandAbility, AbilityScore, Attributes, Class);
        }
        // === Print Results ===
        System.out.println("Name: " + Name);
        System.out.println("Race: " + Race);
        System.out.println("Class: " + Class);
        System.out.println("Alignment: "+ Alignment);
        System.out.println("Attributes: ");
        for(int indx = 0; indx < Attributes.length; indx++)
            System.out.println(Attributes[indx]+" "+ AbilityScore[indx]);
    }

    private void AbilityGenerator(Boolean RandomAbility, int[] AbilityScore, String[] Attributes, String Class)
    {
        if(RandomAbility == false)
        {
            int[] StandardSet = {15, 14, 13, 12, 10, 8}; // For later implementation
            for(int i = 0; i < AbilityScore.length; i++)
            {
                AbilityScore[i] = StandardSet[i];
            }
            // Later implementation: Selecting the scores for each attribute.
        }
        else{
            int[] DiceSet = new int[4];
            int l = 0;
            int r = DiceSet.length;
            int Score;
            // StandardSet_Class - used to determine attributes based on chosen Class

            // Randomized Ability Score
            for(int i = 0; i < AbilityScore.length; i++)
            {
                // Roll Four Dice
                for(int j = 0; j < DiceSet.length; j++)
                {
                    int DiceRoll = ThreadLocalRandom.current().nextInt(1, 7);
                    DiceSet[j] = DiceRoll;
                }
                // Sort Dice Rolls
                SortRolls(DiceSet, l, r);
                Score = DiceSet[1] + DiceSet[2] + DiceSet[3];
                AbilityScore[i] = Score;
            }

            // Later implementation: Selecting the scores for each attribute.
        }
    }

    private static int SortRolls(int[] DiceSet, int l, int r)
    {
        int temp[] = new int[DiceSet.length];
        return _SortRolls(DiceSet, temp, l, r-1);
    }

    private static int _SortRolls(int[] DiceSet, int[] temp, int l, int r)
    {
        int total = 0;
        int mid;
        if(r > l)
        {
            mid = (r + l) / 2;
            total = _SortRolls(DiceSet, temp, l, mid);
            total += _SortRolls(DiceSet, temp, mid+1, r);
            total += _Merge(DiceSet, temp, l, mid+1, r);
//            _SortRolls(DiceSet, temp, l, mid);
//            _SortRolls(DiceSet, temp, mid+1, r);
//            _Merge(DiceSet, temp, l, mid+1, r);
        }

        return total;
    }

    private static int _Merge(int[] DiceSet, int[] temp, int l, int mid, int r)
    {
        int total = 0;
        int i = l;
        int j = mid;
        int k = l;

        // While Loop which Sorts the array
        while((i <= mid -1) && (j<= r))
        {
            if(DiceSet[i] <= DiceSet[j])
            {
                temp[k++] = DiceSet[i++];
            }
            else
            {
                temp[k++] = DiceSet[j++];
            }
        }
        while(i <= mid -1)
            temp[k++] = DiceSet[i++];

        while(j<= r)
            temp[k++] = DiceSet[j++];

        for(i = l; i <= r; i++)
            DiceSet[i] = temp[i];

        //total = DiceSet[1] = DiceSet[2] + DiceSet[3];

        return total;
    }

    private static int[] AttributeAllocation(int[] AbilityScore)
    {
        // Implement Choice of Allocation
        return AbilityScore;
    }

    private String RaceGenerator(String Race)
    {
        String[] Races = {"Human", "Elf", "Dwarf", "Orc", "Half-Elf", "Gnome", "Tiss", "Lizard", "Norn", "Charr"};
        int max = Races.length - 1;
        int index = ThreadLocalRandom.current().nextInt(0, max);
        Race = Races[index];

        return Race;
    }
    private String ClassGenerator(String Class)
    {
        String[] Classes = {"Barbarian", "Bard", "Druid", "Engineer", "Monk", "Paladin", "Ranger", "Rogue", "Sorcerer", "Warlock", "Wizard"};
        int max = Classes.length - 1;
        int index = ThreadLocalRandom.current().nextInt(0, max);
        Class = Classes[index];

        return Class;
    }

    private String AlignmentGenerator(String Alignment)
    {
        String[] Spectrum = {"Good", "Neutral", "Evil"};
        String[] Chaos = {"Lawful", "Neutral", "Chaotic"};
        int max = Spectrum.length - 1;
        int max2 = Chaos.length - 1;
        int indexS = ThreadLocalRandom.current().nextInt(0, max);
        int indexC = ThreadLocalRandom.current().nextInt(0, max2);
        Alignment = Chaos[indexC] + " " + Spectrum[indexS];
        if(Alignment == "Neutral Neutral")
            Alignment = "Neutral";
        return Alignment;
    }
}
