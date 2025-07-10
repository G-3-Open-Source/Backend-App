package pe.edu.upc.center.backendNutriSmart.tracking.application.internal.outboundservices.acl;


import org.springframework.stereotype.Service;
import pe.edu.upc.center.backendNutriSmart.profiles.domain.model.aggregates.UserProfile;
import pe.edu.upc.center.backendNutriSmart.profiles.domain.model.queries.GetUserProfileByIdQuery;
import pe.edu.upc.center.backendNutriSmart.profiles.domain.model.queries.GetAllUserProfilesQuery;
import pe.edu.upc.center.backendNutriSmart.profiles.domain.services.UserProfileQueryService;

import java.util.List;
import java.util.Optional;

/**
 * External Profile Service
 * This service provides an ACL (Anti-Corruption Layer) for other bounded contexts
 * to interact with the Profile Profile domain without directly coupling to its internal structure.
 */
@Service
public class ExternalProfileService {

    private final UserProfileQueryService queryService;

    public ExternalProfileService(UserProfileQueryService queryService) {
        this.queryService = queryService;
    }

    /**
     * Fetches a user profile by its ID
     * @param profileId The ID of the profile to fetch
     * @return Optional containing the UserProfile if found, empty otherwise
     */
    public Optional<UserProfile> fetchUserProfileById(Long profileId) {
        if (profileId == null || profileId <= 0) {
            return Optional.empty();
        }
        return queryService.handle(new GetUserProfileByIdQuery(profileId));
    }

    /**
     * Fetches all user profiles
     * @return List of all UserProfile entities
     */
    public List<UserProfile> fetchAllUserProfiles() {
        return queryService.handle(new GetAllUserProfilesQuery());
    }

    /**
     * Checks if a user profile exists by ID
     * @param profileId The ID to check
     * @return true if the profile exists, false otherwise
     */
    public boolean existsUserProfileById(Long profileId) {
        return fetchUserProfileById(profileId).isPresent();
    }

    /**
     * Gets the count of all user profiles
     * @return The total number of user profiles
     */
    public long getUserProfileCount() {
        return fetchAllUserProfiles().size();
    }
}