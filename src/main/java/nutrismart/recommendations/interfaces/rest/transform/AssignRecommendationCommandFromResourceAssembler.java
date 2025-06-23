package nutrismart.recommendations.interfaces.rest.transform;

import nutrismart.recommendations.domain.model.commands.AssignRecommendationCommand;
import nutrismart.recommendations.interfaces.rest.resources.AssignRecommendationResource;

public class AssignRecommendationCommandFromResourceAssembler {

    public static AssignRecommendationCommand toCommandFromResource(AssignRecommendationResource resource)
    {
        return new AssignRecommendationCommand(
                resource.userId(),
                resource.templateId(),
                resource.reason(),
                resource.notes(),
                resource.timeOfDay(),
                resource.score(),
                resource.status()
        );
    }
}
