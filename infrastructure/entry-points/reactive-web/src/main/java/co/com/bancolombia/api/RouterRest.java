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
  private static final String BRANCH_ENDPOINT = "/branch";
  private static final String PRODUCT_ENDPOINT = "/product";
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

  @Bean
  @RouterOperation(
    path = "/v1/api/branch",
    method = RequestMethod.POST,
    operation = @Operation(
      operationId = "createBranch",
      summary = "Create a new branch",
      description = "Registers a new branch for a franchise.",
      tags = {"Branch"},
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
        required = true,
        description = "Branch data to create",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = co.com.bancolombia.api.request.CreateBranchRequest.class)
        )
      ),
      responses = {
        @ApiResponse(
          responseCode = "200",
          description = "Branch created successfully"
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
  public RouterFunction<ServerResponse> createBranchRouter(Handler handler) {
    return route(POST(BASE_URL + BRANCH_ENDPOINT), handler::createBranch);
  }

  @Bean
  @RouterOperation(
    path = "/v1/api/franchise/{franchiseId}/branch",
    method = RequestMethod.GET,
    operation = @Operation(
      operationId = "findBranchesByFranchise",
      summary = "List branches by franchise",
      description = "Returns all branches for the given franchise.",
      tags = {"Branch"},
      parameters = {
        @io.swagger.v3.oas.annotations.Parameter(
          name = "franchiseId",
          description = "Franchise ID",
          required = true,
          in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
          schema = @Schema(type = "integer", format = "int64"),
          example = "1"
        )
      },
      responses = {
        @ApiResponse(
          responseCode = "200",
          description = "Branches returned successfully"
        ),
        @ApiResponse(
          responseCode = "500",
          description = "Internal server error",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        )
      }
    )
  )
  public RouterFunction<ServerResponse> findBranchesByFranchiseRouter(Handler handler) {
    return route(GET(BASE_URL + FRANCHISE_ENDPOINT + "/{franchiseId}" + BRANCH_ENDPOINT), handler::findBranchesByFranchise);
  }

  @Bean
  @RouterOperation(
    path = "/v1/api/branch/{id}",
    method = RequestMethod.DELETE,
    operation = @Operation(
      operationId = "deleteBranch",
      summary = "Delete branch",
      description = "Deletes a branch by id.",
      tags = {"Branch"},
      parameters = {
        @io.swagger.v3.oas.annotations.Parameter(
          name = "id",
          description = "Branch ID to delete",
          required = true,
          in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
          schema = @Schema(type = "integer", format = "int64"),
          example = "1"
        )
      },
      responses = {
        @ApiResponse(
          responseCode = "200",
          description = "Branch deleted successfully"
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
  public RouterFunction<ServerResponse> deleteBranchRouter(Handler handler) {
    return route(DELETE(BASE_URL + BRANCH_ENDPOINT + "/{id}"), handler::deleteBranch);
  }

  @Bean
  @RouterOperation(
    path = "/v1/api/branch",
    method = RequestMethod.PUT,
    operation = @Operation(
      operationId = "updateBranch",
      summary = "Update branch",
      description = "Updates a branch by id.",
      tags = {"Branch"},
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
        required = true,
        description = "Branch data to update",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = co.com.bancolombia.api.request.UpdateBranchRequest.class)
        )
      ),
      responses = {
        @ApiResponse(
          responseCode = "200",
          description = "Branch updated successfully"
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
  public RouterFunction<ServerResponse> updateBranchRouter(Handler handler) {
    return route(PUT(BASE_URL + BRANCH_ENDPOINT), handler::updateBranch);
  }

  @Bean
  @RouterOperation(
    path = "/v1/api/product",
    method = RequestMethod.POST,
    operation = @Operation(
      operationId = "createProduct",
      summary = "Create a new product",
      description = "Registers a new product for a branch.",
      tags = {"Product"},
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
        required = true,
        description = "Product data to create",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = co.com.bancolombia.api.request.CreateProductRequest.class)
        )
      ),
      responses = {
        @ApiResponse(
          responseCode = "200",
          description = "Product created successfully"
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
  public RouterFunction<ServerResponse> createProductRouter(Handler handler) {
    return route(POST(BASE_URL + PRODUCT_ENDPOINT), handler::createProduct);
  }

  @Bean
  @RouterOperation(
    path = "/v1/api/branch/{branchId}/product",
    method = RequestMethod.GET,
    operation = @Operation(
      operationId = "findProductsByBranch",
      summary = "List products by branch",
      description = "Returns all products for the given branch.",
      tags = {"Product"},
      parameters = {
        @io.swagger.v3.oas.annotations.Parameter(
          name = "branchId",
          description = "Branch ID",
          required = true,
          in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
          schema = @Schema(type = "integer", format = "int64"),
          example = "1"
        )
      },
      responses = {
        @ApiResponse(
          responseCode = "200",
          description = "Products returned successfully"
        ),
        @ApiResponse(
          responseCode = "500",
          description = "Internal server error",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        )
      }
    )
  )
  public RouterFunction<ServerResponse> findProductsByBranchRouter(Handler handler) {
    return route(GET(BASE_URL + BRANCH_ENDPOINT + "/{branchId}" + PRODUCT_ENDPOINT), handler::findProductsByBranch);
  }

  @Bean
  @RouterOperation(
    path = "/v1/api/product",
    method = RequestMethod.PUT,
    operation = @Operation(
      operationId = "updateProduct",
      summary = "Update product",
      description = "Updates a product by id.",
      tags = {"Product"},
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
        required = true,
        description = "Product data to update",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = co.com.bancolombia.api.request.UpdateProductRequest.class)
        )
      ),
      responses = {
        @ApiResponse(
          responseCode = "200",
          description = "Product updated successfully"
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
  public RouterFunction<ServerResponse> updateProductRouter(Handler handler) {
    return route(PUT(BASE_URL + PRODUCT_ENDPOINT), handler::updateProduct);
  }

  @Bean
  @RouterOperation(
    path = "/v1/api/product/{id}",
    method = RequestMethod.DELETE,
    operation = @Operation(
      operationId = "deleteProduct",
      summary = "Delete product",
      description = "Deletes a product by id.",
      tags = {"Product"},
      parameters = {
        @io.swagger.v3.oas.annotations.Parameter(
          name = "id",
          description = "Product ID to delete",
          required = true,
          in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
          schema = @Schema(type = "integer", format = "int64"),
          example = "1"
        )
      },
      responses = {
        @ApiResponse(
          responseCode = "200",
          description = "Product deleted successfully"
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
  public RouterFunction<ServerResponse> deleteProductRouter(Handler handler) {
    return route(DELETE(BASE_URL + PRODUCT_ENDPOINT + "/{id}"), handler::deleteProduct);
  }

  @Bean
  @RouterOperation(
    path = "/v1/api/franchise/{id}/branches-top-product",
    method = RequestMethod.GET,
    operation = @Operation(
      operationId = "getFranchiseWithBranchesTopProduct",
      summary = "Get franchise with branches and top product per branch",
      description = "Returns the franchise, all its branches, and the top-stock product for each branch.",
      tags = {"Franchise"},
      parameters = {
        @io.swagger.v3.oas.annotations.Parameter(
          name = "id",
          description = "Franchise ID",
          required = true,
          in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
          schema = @Schema(type = "integer", format = "int64"),
          example = "1"
        )
      },
      responses = {
        @ApiResponse(
          responseCode = "200",
          description = "Data returned successfully"
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
  public RouterFunction<ServerResponse> getFranchiseWithBranchesTopProductRouter(Handler handler) {
    return route(GET(BASE_URL + FRANCHISE_ENDPOINT + "/{id}/branches-top-product"), handler::getFranchiseWithBranchesTopProduct);
  }
}