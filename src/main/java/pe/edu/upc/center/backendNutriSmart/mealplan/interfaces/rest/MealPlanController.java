package pe.edu.upc.center.backendNutriSmart.mealplan.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.backendNutriSmart.mealplan.domain.model.aggregates.MealPlan;
import pe.edu.upc.center.backendNutriSmart.mealplan.domain.model.commands.DeleteMealPlanCommand;
import pe.edu.upc.center.backendNutriSmart.mealplan.domain.model.queries.GetAllMealPlanQuery;
import pe.edu.upc.center.backendNutriSmart.mealplan.domain.model.queries.GetMealPlanByIdQuery;
import pe.edu.upc.center.backendNutriSmart.mealplan.domain.services.MealPlanCommandService;
import pe.edu.upc.center.backendNutriSmart.mealplan.domain.services.MealPlanQueryService;
import pe.edu.upc.center.backendNutriSmart.mealplan.interfaces.rest.resources.CreateMealPlanResource;
import pe.edu.upc.center.backendNutriSmart.mealplan.interfaces.rest.resources.MealPlanResource;
import pe.edu.upc.center.backendNutriSmart.mealplan.interfaces.rest.transform.CreateMealPlanCommandFromResourceAssembler;
import pe.edu.upc.center.backendNutriSmart.mealplan.interfaces.rest.transform.MealPlanResourceFromEntityAssembler;
import pe.edu.upc.center.backendNutriSmart.mealplan.interfaces.rest.transform.UpdateMealPlanCommandFromResourceAssembler;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/meal-plan", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Meal Plan", description = "Meal plans Management Endpoints")
public class MealPlanController {
    private final MealPlanCommandService mealPlanCommandService;
    private final MealPlanQueryService mealPlanQueryService;

    public MealPlanController(MealPlanQueryService mealPlanQueryService, MealPlanCommandService mealPlanCommandService) {
        this.mealPlanQueryService = mealPlanQueryService;
        this.mealPlanCommandService = mealPlanCommandService;
    }

    @PostMapping
    public ResponseEntity<MealPlanResource> createStudent(@RequestBody CreateMealPlanResource resource) {
        // Create meal plan
        var createMealPlanCommand = CreateMealPlanCommandFromResourceAssembler.toCommandFromResource(resource);
        var mealPlanEntity = this.mealPlanCommandService.handle(createMealPlanCommand);
        // Validate if meal plan is empty
        if (mealPlanEntity.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        // Fetch meal plan
        var getMealPlanByIdQuery = new GetMealPlanByIdQuery(mealPlanEntity.get().getId());
        var mealplan = this.mealPlanQueryService.handle(getMealPlanByIdQuery);
        if (mealplan.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

//        var studentResource = this.externalProfileService.fetchStudentResourceFromProfileId(student.get()).get();
        return new ResponseEntity<>(MealPlanResourceFromEntityAssembler.toResourceFromEntity(mealplan.get()), HttpStatus.CREATED);

    }
    @GetMapping
    public ResponseEntity<List<MealPlanResource>> getAllMealPlans() {
        var getAllMealPlansQuery = new GetAllMealPlanQuery();
        var mealPlans = this.mealPlanQueryService.handle(
                getAllMealPlansQuery);
        return ResponseEntity.ok(
                mealPlans.stream()
                        .map(MealPlanResourceFromEntityAssembler::toResourceFromEntity)
                        .toList()
        );
    }

    @GetMapping("/{mealPlanId}")
    public ResponseEntity<MealPlanResource> getMealPlanById(@PathVariable int mealPlanId) {
        var getMealPlanByIdQuery = new GetMealPlanByIdQuery(mealPlanId);
        var mealPlanResource = this.mealPlanQueryService.handle(getMealPlanByIdQuery);
        if (mealPlanResource.isEmpty())
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(MealPlanResourceFromEntityAssembler.toResourceFromEntity(mealPlanResource.get()));
    }

    @PutMapping("/{mealPlanId}")
    public ResponseEntity<MealPlanResource> updateStudent(@PathVariable int mealPlanId, @RequestBody MealPlanResource resource) {
        var updateMealPlanCommand = UpdateMealPlanCommandFromResourceAssembler.toCommandFromResource(resource, mealPlanId);
        var optionalMealPlan = this.mealPlanCommandService.handle(updateMealPlanCommand);
        if (optionalMealPlan.isEmpty())
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(MealPlanResourceFromEntityAssembler.toResourceFromEntity(optionalMealPlan.get()));
    }

    @DeleteMapping("/{mealPlanId}")
    public ResponseEntity<?> deleteMealPlan(@PathVariable int mealPlanId) {
        var deleteMealPlanCommand = new DeleteMealPlanCommand(mealPlanId);
        this.mealPlanCommandService.handle(deleteMealPlanCommand);
        return ResponseEntity.noContent().build();
    }
}
