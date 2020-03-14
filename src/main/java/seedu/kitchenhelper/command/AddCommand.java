package seedu.kitchenhelper.command;

import seedu.kitchenhelper.exception.KitchenHelperException;
import seedu.kitchenhelper.object.Chore;
import seedu.kitchenhelper.object.Recipe;
import seedu.kitchenhelper.object.ingredient.Ingredient;
import seedu.kitchenhelper.storage.Storage;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Perform addition-related commands.
 */
public class AddCommand extends Command {
    
    public static final String COMMAND_WORD = "add";
    public HashMap<String[], Integer> parsedIngr;

    /**
     * Set the object's type.
     *
     * @param object full user input string excluding the action word.
     */
    public void setTypeOfObject(String object) {
        String[] attributes = object.split("\\s+");
        if (attributes[0].equalsIgnoreCase("recipe")) {
            objectType = "recipe";
        } else if (attributes[0].equalsIgnoreCase("ingredient")) {
            objectType = "ingredient";
        } else if (attributes[0].equalsIgnoreCase("chore")) {
            objectType = "chore";
        }
    }

    /**
     * Set the attributes of the Command class.
     *
     * @param rawString full user input string.
     * @param ingrAndQty a hashmap of ingredient with [ingredientName, ingredientCategory] as key
     *                   and ingredientQuantity as value
     */
    public void setAttributesOfCmd(String rawString, HashMap<String[], Integer> ingrAndQty) {
        setTypeOfObject(rawString);
        setObjectVariables(rawString);
        setAction();
        this.parsedIngr = ingrAndQty;
    }

    public void setObjectVariables(String rawString) {
        objectVariables = rawString;
    }

    public void setAction() {
        actionType = COMMAND_WORD;
    }

    public void executeIngredientStorage(ArrayList<Ingredient> ingredientList, Storage storage){

    }

    public void executeChoreStorage(ArrayList<Chore> choreList, Storage storage){

    }

    public void executeRecipeStorage(ArrayList<Recipe> recipeList, Storage storage){

    }
    
    public void addIngredients(String attributes, ArrayList<Ingredient> ingredientList) {

        //Storage.saveIngredientData(ingredientList);
    }

    @Override
    public String addRecipe(String attributes, ArrayList<Recipe> recipeList) throws KitchenHelperException {
        Recipe freshRecipe = new Recipe();
        freshRecipe.setRecipeName(attributes);
        freshRecipe.addIngredientsToRecipe(parsedIngr);
        recipeList.add(freshRecipe);
        Storage.saveRecipeData(recipeList);
        return freshRecipe.recipeName + " Recipe has been created with "
                + freshRecipe.recipeIngrQty + " ingredients inside.";
    }
    
    @Override
    public String addChore(String objectVariables, ArrayList<Chore> choreList) {
        String feedbackToUser;
        try {
            String[] objectTypeAndOthers = objectVariables.split("chore ", 2);
            String[] descriptionAndDate = objectTypeAndOthers[1].trim().split("/by");
            String description = descriptionAndDate[0].trim();
            String date = descriptionAndDate[1].trim();
            Chore newChore = new Chore(description, date);
            choreList.add(newChore);
            newChore.setEditType(COMMAND_WORD);
            feedbackToUser = String.format(Chore.MESSAGE_SUCCESS,
                    newChore.editType, newChore, choreList.size(), newChore.checkSingular(choreList));
        } catch (IndexOutOfBoundsException e) {
            feedbackToUser = "You need to add a date with \"/by\" in the description.";
        }
        Storage.saveChoreData(choreList);
        return feedbackToUser;
    }
    
    @Override
    public CommandResult execute(ArrayList<Ingredient> ingredientList, ArrayList<Recipe> recipeList,
                                 ArrayList<Chore> choreList) throws KitchenHelperException {
        return super.execute(ingredientList, recipeList, choreList);
    }
}
