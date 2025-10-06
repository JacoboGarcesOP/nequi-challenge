package co.com.bancolombia.api;

import co.com.bancolombia.api.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
  private static final String FRANCHISE_ENDPOINT = "/franchise";
  private static final String BASE_URL = "/v1/api";

  @Bean
  @RouterOperation(
    path = "/v1/api/franchise",
    method = RequestMethod.POST,
    operation = @Operation(
      operationId = "createFranchise",
      summary = "Create a new franchise",
      description = "Registers a new franchise by name.",
      tags = {"Franchise"},
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
        required = true,
        description = "Franchise data to create",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = co.com.bancolombia.api.request.CreateFranchiseRequest.class)
        )
      ),
      responses = {
        @ApiResponse(
          responseCode = "200",
          description = "Franchise created successfully"
        ),
        @ApiResponse(
          responseCode = "400",
          description = "Validation or business error",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
          responseCode = "500",
          description = "Internal server error",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        )
      }
    )
  )
  public RouterFunction<ServerResponse> createFranchiseRouter(Handler handler) {
    return route(POST(BASE_URL + FRANCHISE_ENDPOINT), handler::createFranchise);
  }

  @Bean
  @RouterOperation(
    path = "/v1/api/franchise",
    method = RequestMethod.GET,
    operation = @Operation(
      operationId = "findAllFranchises",
      summary = "List all franchises",
      description = "Returns all franchises.",
      tags = {"Franchise"},
      responses = {
        @ApiResponse(
          responseCode = "200",
          description = "Franchises returned successfully"
        ),
        @ApiResponse(
          responseCode = "500",
          description = "Internal server error",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        )
      }
    )
  )
  public RouterFunction<ServerResponse> findAllFranchisesRouter(Handler handler) {
    return route(GET(BASE_URL + FRANCHISE_ENDPOINT), handler::findAllFranchises);
  }

  @Bean
  @RouterOperation(
    path = "/v1/api/franchise",
    method = RequestMethod.PUT,
    operation = @Operation(
      operationId = "updateFranchise",
      summary = "Update franchise",
      description = "Updates a franchise's name by id.",
      tags = {"Franchise"},
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
        required = true,
        description = "Franchise data to update",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = co.com.bancolombia.api.request.UpdateFranchiseRequest.class)
        )
      ),
      responses = {
        @ApiResponse(
          responseCode = "200",
          description = "Franchise updated successfully"
        ),
        @ApiResponse(
          responseCode = "400",
          description = "Validation or business error",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
          responseCode = "500",
          description = "Internal server error",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        )
      }
    )
  )
  public RouterFunction<ServerResponse> updateFranchiseRouter(Handler handler) {
    return route(PUT(BASE_URL + FRANCHISE_ENDPOINT), handler::updateFranchise);
  }

  @Bean
  @RouterOperation(
    path = "/v1/api/franchise/{id}",
    method = RequestMethod.DELETE,
    operation = @Operation(
      operationId = "deleteFranchise",
      summary = "Delete franchise",
      description = "Deletes a franchise by id.",
      tags = {"Franchise"},
      parameters = {
        @io.swagger.v3.oas.annotations.Parameter(
          name = "id",
          description = "Franchise ID to delete",
          required = true,
          in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
          schema = @Schema(type = "integer", format = "int64"),
          example = "1"
        )
      },
      responses = {
        @ApiResponse(
          responseCode = "200",
          description = "Franchise deleted successfully"
        ),
        @ApiResponse(
          responseCode = "400",
          description = "Business or domain error",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
          responseCode = "500",
          description = "Internal server error",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        )
      }
    )
  )
  public RouterFunction<ServerResponse> deleteFranchiseRouter(Handler handler) {
    return route(DELETE(BASE_URL + FRANCHISE_ENDPOINT + "/{id}"), handler::deleteFranchise);
  }
}