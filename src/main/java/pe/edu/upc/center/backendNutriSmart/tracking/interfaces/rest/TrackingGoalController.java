package pe.edu.upc.center.backendNutriSmart.tracking.interfaces.rest;

import pe.edu.upc.center.backendNutriSmart.tracking.application.internal.commandservices.MacronutrientValuesCommandServiceImpl;
import pe.edu.upc.center.backendNutriSmart.tracking.application.internal.commandservices.TrackingGoalCommandServiceImpl;
import pe.edu.upc.center.backendNutriSmart.tracking.domain.model.commands.CreateMacronutrientValuesCommand;
import pe.edu.upc.center.backendNutriSmart.tracking.domain.model.commands.CreateTrackingGoalCommand;
import pe.edu.upc.center.backendNutriSmart.tracking.domain.model.queries.GetTargetMacronutrientsQuery;
import pe.edu.upc.center.backendNutriSmart.tracking.domain.model.valueobjects.UserId;
import pe.edu.upc.center.backendNutriSmart.tracking.infrastructure.persistence.jpa.repositories.MacronutrientValuesRepository;
import pe.edu.upc.center.backendNutriSmart.tracking.interfaces.rest.resources.CreateTrackingGoalResource;
import pe.edu.upc.center.backendNutriSmart.tracking.interfaces.rest.resources.MacronutrientValuesResource;
import pe.edu.upc.center.backendNutriSmart.tracking.interfaces.rest.transform.MacronutrientValuesResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.backendNutriSmart.tracking.domain.model.queries.GetTrackingGoalByUserIdQuery;
import pe.edu.upc.center.backendNutriSmart.tracking.domain.services.TrackingGoalQueryService;
import pe.edu.upc.center.backendNutriSmart.tracking.interfaces.rest.resources.TrackingGoalResource;
import pe.edu.upc.center.backendNutriSmart.tracking.interfaces.rest.transform.TrackingGoalResourceFromEntityAssembler;

@RestController
@RequestMapping(value = "/api/v1/tracking-goals", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Tracking Goals", description = "Tracking Goals Management Endpoints")
public class TrackingGoalController {

    private final TrackingGoalQueryService trackingGoalQueryService;
    private final MacronutrientValuesCommandServiceImpl macronutrientValuesCommandService;
    private final TrackingGoalCommandServiceImpl trackingGoalCommandService;
    private final MacronutrientValuesRepository macronutrientValuesRepository;


    public TrackingGoalController(TrackingGoalQueryService trackingGoalQueryService,  MacronutrientValuesCommandServiceImpl macronutrientValuesCommandService,
                                  TrackingGoalCommandServiceImpl trackingGoalCommandService,
                                  MacronutrientValuesRepository macronutrientValuesRepository) {
        this.trackingGoalQueryService = trackingGoalQueryService;
        this.macronutrientValuesCommandService = macronutrientValuesCommandService;
        this.trackingGoalCommandService = trackingGoalCommandService;
        this.macronutrientValuesRepository = macronutrientValuesRepository;
    }

    @Operation(
            summary = "Get tracking goal by user ID",
            description = "Fetch tracking goal information for a specific user",
            operationId = "getTrackingGoalByUserId",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TrackingGoalResource.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Tracking goal not found"
                    )
            }
    )
    @GetMapping("/user/{userId}")
    public ResponseEntity<TrackingGoalResource> getTrackingGoalByUserId(@PathVariable Long userId) {
        var getTrackingGoalByUserIdQuery = new GetTrackingGoalByUserIdQuery(new UserId(userId));
        var optionalTrackingGoal = this.trackingGoalQueryService.handle(getTrackingGoalByUserIdQuery);

        if (optionalTrackingGoal.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var trackingGoalResource = TrackingGoalResourceFromEntityAssembler.toResource(optionalTrackingGoal.get());
        return ResponseEntity.ok(trackingGoalResource);
    }

    // AGREGA ESTE NUEVO MÉTODO:
    @Operation(
            summary = "Get target macronutrients by tracking goal ID",
            description = "Fetch target macronutrients for a specific tracking goal",
            operationId = "getTargetMacronutrientsByTrackingGoalId",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MacronutrientValuesResource.class) // Necesitarás crear este Resource
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Target macronutrients not found"
                    )
            }
    )
    @GetMapping("/{trackingGoalId}/target-macros")
    public ResponseEntity<MacronutrientValuesResource> getTargetMacronutrientsByTrackingGoalId(@PathVariable Long trackingGoalId) {
        var getTargetMacronutrientsQuery = new GetTargetMacronutrientsQuery(trackingGoalId);
        var optionalMacros = this.trackingGoalQueryService.handle(getTargetMacronutrientsQuery);

        if (optionalMacros.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var macronutrientsResource = MacronutrientValuesResourceFromEntityAssembler.toResource(optionalMacros.get()); // Necesitarás crear este Assembler
        return ResponseEntity.ok(macronutrientsResource);
    }

    @Operation(
            summary = "Create a new Tracking Goal",
            description = "Creates a new TrackingGoal with target macronutrient values",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Tracking goal created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    @PostMapping
    public ResponseEntity<Long> createTrackingGoal(@RequestBody CreateTrackingGoalResource resource) {
        var userId = new UserId(resource.userId());

        var createMacroCommand = new CreateMacronutrientValuesCommand(
                null, // ID es generado
                resource.targetMacros().calories(),
                resource.targetMacros().carbs(),
                resource.targetMacros().proteins(),
                resource.targetMacros().fats()
        );

        // Guardar los valores de macronutrientes
        Long macroId = macronutrientValuesCommandService.handle(createMacroCommand);

        // Buscar la entidad MacronutrientValues recién creada
        var macronutrientOpt = macronutrientValuesRepository.findById(macroId);
        if (macronutrientOpt.isEmpty()) {
            return ResponseEntity.badRequest().build(); // debería ser raro que ocurra
        }

        var trackingGoalCommand = new CreateTrackingGoalCommand(userId, macronutrientOpt.get());

        Long goalId = trackingGoalCommandService.handle(trackingGoalCommand);

        return ResponseEntity.status(201).body(goalId);
    }

}