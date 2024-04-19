package habib.Login.controller;

import habib.Login.model.Recipe;
import habib.Login.model.User;
import habib.Login.service.RecipeService;
import habib.Login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/recipe")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private UserService userService;

    @PostMapping("/user/{userId}")
    public Recipe createRecipe(@RequestBody Recipe recipe, @PathVariable Long userId) throws Exception {

        User user = userService.findUserById(userId);
        Recipe createdRecipe = recipeService.createRecipe(recipe, user);

        return createdRecipe;
    }

    @GetMapping()
    public List<Recipe> getAllRecipe() throws Exception {

        List<Recipe> recipes = recipeService.findAllRecipe();

        return recipes;
    }

    @DeleteMapping("/{recipeId}")
    public String deleteAllRecipe(@PathVariable Long recipeId) throws Exception {

        recipeService.deleteRecipe(recipeId);
        return "Recipe deleted successfully!";

    }

    @PutMapping("/{userId}")
    public Recipe updateRecipe(@RequestBody Recipe recipe, @PathVariable Long userId) throws Exception {

        Recipe updatedRecipe = recipeService.updateRecipe(recipe, userId);

        return updatedRecipe;
    }

    @PutMapping("/{id}/like/user/{userId}")
    public Recipe likeRecipe(@PathVariable Long userId, @PathVariable Long id) throws Exception {


        User user = userService.findUserById(userId);

        Recipe updatedRecipe = recipeService.likeRecipe(id, user);

        return updatedRecipe;
    }
}
