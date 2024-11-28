//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import javax.swing.*;
import java.util.Scanner;
import java.util.ArrayList;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        boolean done = false;
        String menuChoice;
        boolean needsToBeSaved = false;
        ArrayList<String> myArrList = new ArrayList<>();
        JFileChooser chooser = new JFileChooser();

        //It does say there is an error in my program, but I have yet to encounter it in any fashion despite running it multiple times.
        //Now there needs to be a do while loop that will "trap" the user until they decide to exit.
        do {
            menuChoice = "";
            menuChoice = SafeInput.getRegExString(in, "Do you want to: Add something to the list? Delete an item from the list? Move an item in the list?\nInsert an item into the list? Open a new file? View the list? Quit the program? [A,D,M,I,O,V,Q]", "[AaDdMmIiOoVvQq]");

            //System.out.println(); //Clear line to break things up

            switch (menuChoice)
            {
                case "A","a":
                    addOption(in, myArrList, needsToBeSaved);
                    break;
                case "D","d":
                    deleteOption(in,myArrList, needsToBeSaved);
                    break;
                case "I","i":
                    insertOption(in,myArrList, needsToBeSaved);
                    break;
                case "V","v":
                    viewOption(myArrList);
                    break;
                case "Q","q":
                    done = quitOption(in, needsToBeSaved);
                    break;
                case "O","o":
                    openOption(chooser,in, myArrList, needsToBeSaved);
                    break;
                case "M","m":
                    moveOption();
                    break;
                case "S","s":
                    saveOption(myArrList, needsToBeSaved);
                    break;
                case "C","c":
                    clearOption();
                    break;
            }

            //System.out.println(); //clearing a bit of area before printing the current list
            //viewOption(myArrList); //Removed because it feels like it invalidates the print menu option and might rapidly spam output box at large list size.
            System.out.println(); //Line here might make things look less congested.
        } while(!done);
    }

    public static boolean quitOption(Scanner in, boolean needsToBeSaved)
    {
        boolean quit = false;
        if(needsToBeSaved)
        {
            quit = SafeInput.getYNConfirm(in, "Are you sure that you want to quit? [Y/N]");
            return quit;
        }
        else
        {
            quit = SafeInput.getYNConfirm(in, "You have unsaved work. Are you sure that you want to quit? [Y/N]");
            return quit;
        }
    }
    public static void viewOption(ArrayList<String> myArrList)
    {
        if(!myArrList.isEmpty())
            for( int x = 0; x < myArrList.size(); x++)
            {
                System.out.println("\n" + (x + 1) + ": " + myArrList.get(x)); //Trying to make it user-friendly for those who don't count from zero.
            }
        else
        {
            System.out.println("\nNothing to print.");
        }
    }
    public static boolean deleteOption(Scanner in, ArrayList<String> myArrList, boolean needsToBeSaved)
    {
        int removalItem;

        if (myArrList.isEmpty())
        {
            System.out.println("List is empty. Nothing to remove.");
        }
        else { //Something says there is an error at 95, but the method seems to work fine?
            removalItem = SafeInput.getRangedInt(in, "Removing something from the list. Please enter a number between 1 and " + myArrList.size(), 1, myArrList.size());
            myArrList.remove(removalItem - 1);
            System.out.println("\nItem successfully removed from the list.");
            needsToBeSaved = true;
            return needsToBeSaved;
        }
        return needsToBeSaved;
    }

    public static boolean insertOption (Scanner in, ArrayList<String> myArrList, boolean needsToBeSaved)
    {
        int insertIndex;
        String insertItem;

        insertIndex = SafeInput.getRangedInt(in, "To insert an item, please enter a number between 1 and " + myArrList.size(), 1, myArrList.size());
        insertItem = SafeInput.getNonZeroLenString(in, "Please enter an item into the list");

        myArrList.add((insertIndex -1), insertItem);
        needsToBeSaved = true;
        return needsToBeSaved;
    }

    public static boolean addOption (Scanner in, ArrayList<String> myArrList, boolean needsToBeSaved)
    {
        myArrList.add(SafeInput.getNonZeroLenString(in,"Please enter a name"));
        //Feels like a waste to make a one line method that contains usage of a method.
        //But it is by the rubric.
        needsToBeSaved = true;
        return needsToBeSaved;
    }

    public static boolean openOption (JFileChooser chooser, Scanner in, ArrayList<String> myArrList, boolean needsToBeSaved)
    {
        boolean userSaveOverride = false;
        if (needsToBeSaved)
        {
            userSaveOverride = SafeInput.getYNConfirm(in, "You have unsaved work. Are you sure that you want to open a new file? [Y/N]");
            if (userSaveOverride) {

                needsToBeSaved = false;
                return needsToBeSaved;
            }
            else
            {


                return needsToBeSaved;
            }
        }
        else
        {


            needsToBeSaved = false;
            return needsToBeSaved;
        }
    }

    public static void moveOption (Scanner in, ArrayList<String> myArrList, boolean needsToBeSaved)
    {

    }

    public static void saveOption (Scanner in,ArrayList<String> myArrList, boolean needsToBeSaved)
    {

    }

    public static void clearOption (ArrayList<String> myArrList, boolean needsToBeSaved)
    {

    }
}