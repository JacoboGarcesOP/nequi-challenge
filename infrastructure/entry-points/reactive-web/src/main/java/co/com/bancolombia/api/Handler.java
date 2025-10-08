package co.com.bancolombia.api;

import co.com.bancolombia.api.response.ErrorResponse;
import co.com.bancolombia.api.response.FranchiseResponse;
import co.com.bancolombia.api.response.BranchResponse;
import co.com.bancolombia.api.response.ProductResponse;
import co.com.bancolombia.usecase.franchise.dto.FranchiseWithBranchesResponse;
import co.com.bancolombia.api.request.CreateFranchiseRequest;
import co.com.bancolombia.api.request.UpdateFranchiseRequest;
import co.com.bancolombia.api.request.CreateBranchRequest;
import co.com.bancolombia.api.request.CreateProductRequest;
import co.com.bancolombia.api.request.UpdateProductRequest;
import co.com.bancolombia.api.request.UpdateBranchRequest;
import co.com.bancolombia.model.franchise.exceptions.DomainException;
import co.com.bancolombia.usecase.franchise.CreateFranchiseUseCase;
import co.com.bancolombia.usecase.franchise.DeleteFranchiseUseCase;
import co.com.bancolombia.usecase.franchise.FindAllFranchisesUseCase;
import co.com.bancolombia.usecase.franchise.UpdateFranchiseUseCase;
import co.com.bancolombia.usecase.branch.CreateBranchUseCase;
import co.com.bancolombia.usecase.branch.FindBranchesByFranchiseUseCase;
import co.com.bancolombia.usecase.branch.DeleteBranchUseCase;
import co.com.bancolombia.usecase.branch.UpdateBranchUseCase;
import co.com.bancolombia.usecase.product.CreateProductUseCase;
import co.com.bancolombia.usecase.product.FindProductsByBranchUseCase;
import co.com.bancolombia.usecase.product.UpdateProductUseCase;
import co.com.bancolombia.usecase.product.DeleteProductUseCase;
import co.com.bancolombia.usecase.franchise.GetFranchiseWithBranchesTopProductUseCase;
import co.com.bancolombia.usecase.exception.BussinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class Handler {
  private static final String VALIDATION_ERROR_TEXT = "VALIDATION_ERROR";
  private static final String DOMAIN_ERROR_TEXT = "DOMAIN_ERROR";
  private static final String BUSINESS_ERROR_TEXT = "BUSINESS_ERROR";
  private static final String INTERNAL_ERROR_TEXT = "INTERNAL_ERROR";
  private static final String GENERIC_ERROR_MESSAGE = "An unexpected error occurred";

  private final CreateFranchiseUseCase createFranchiseUseCase;
  private final FindAllFranchisesUseCase findAllFranchisesUseCase;
  private final UpdateFranchiseUseCase updateFranchiseUseCase;
  private final DeleteFranchiseUseCase deleteFranchiseUseCase;
  private final CreateBranchUseCase createBranchUseCase;
  private final FindBranchesByFranchiseUseCase findBranchesByFranchiseUseCase;
  private final DeleteBranchUseCase deleteBranchUseCase;
  private final UpdateBranchUseCase updateBranchUseCase;
  private final CreateProductUseCase createProductUseCase;
  private final FindProductsByBranchUseCase findProductsByBranchUseCase;
  private final UpdateProductUseCase updateProductUseCase;
  private final DeleteProductUseCase deleteProductUseCase;
  private final GetFranchiseWithBranchesTopProductUseCase getFranchiseWithBranchesTopProductUseCase;
  private final Validator validator;


  public Mono<ServerResponse> createFranchise(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(CreateFranchiseRequest.class)
      .doOnSubscribe(s -> log.info("Request received: createFranchise"))
      .doOnNext(req -> {
        Set<ConstraintViolation<CreateFranchiseRequest>> violations = validator.validate(req);
        if (!violations.isEmpty()) {
          throw new ConstraintViolationException(violations);
        }
      })
      .flatMap(req -> createFranchiseUseCase.execute(req.getName()))
      .flatMap(this::buildSuccessResponse)
      .onErrorResume(ConstraintViolationException.class, this::handleValidationException)
      .onErrorResume(DomainException.class, this::handleDomainException)
      .onErrorResume(BussinessException.class, this::handleBusinessException)
      .onErrorResume(Exception.class, this::handleGenericException)
      .doOnError(error -> log.error(GENERIC_ERROR_MESSAGE, error));
  }

  public Mono<ServerResponse> findAllFranchises(ServerRequest serverRequest) {
    return findAllFranchisesUseCase.execute()
      .doOnSubscribe(s -> log.info("Request received: findAllFranchises"))
      .collectList()
      .flatMap(this::buildSuccessResponse)
      .onErrorResume(DomainException.class, this::handleDomainException)
      .onErrorResume(BussinessException.class, this::handleBusinessException)
      .onErrorResume(Exception.class, this::handleGenericException)
      .doOnError(error -> log.error(GENERIC_ERROR_MESSAGE, error));
  }

  public Mono<ServerResponse> updateFranchise(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(UpdateFranchiseRequest.class)
      .doOnSubscribe(s -> log.info("Request received: updateFranchise"))
      .doOnNext(req -> {
        Set<ConstraintViolation<UpdateFranchiseRequest>> violations = validator.validate(req);
        if (!violations.isEmpty()) {
          throw new ConstraintViolationException(violations);
        }
      })
      .flatMap(req -> updateFranchiseUseCase.execute(req.getId(), req.getName()))
      .flatMap(this::buildSuccessResponse)
      .onErrorResume(ConstraintViolationException.class, this::handleValidationException)
      .onErrorResume(co.com.bancolombia.model.franchise.exceptions.DomainException.class, this::handleDomainException)
      .onErrorResume(BussinessException.class, this::handleBusinessException)
      .onErrorResume(Exception.class, this::handleGenericException)
      .doOnError(error -> log.error(GENERIC_ERROR_MESSAGE, error));
  }

  public Mono<ServerResponse> deleteFranchise(ServerRequest serverRequest) {
    Long id = Long.valueOf(serverRequest.pathVariable("id"));
    return deleteFranchiseUseCase.execute(id)
      .doOnSubscribe(s -> log.info("Request received: deleteFranchise id={}", id))
      .flatMap(ignored -> ServerResponse.noContent().build())
      .onErrorResume(co.com.bancolombia.model.franchise.exceptions.DomainException.class, this::handleDomainException)
      .onErrorResume(BussinessException.class, this::handleBusinessException)
      .onErrorResume(Exception.class, this::handleGenericException)
      .doOnError(error -> log.error(GENERIC_ERROR_MESSAGE, error));
  }

  public Mono<ServerResponse> createBranch(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(CreateBranchRequest.class)
      .doOnSubscribe(s -> log.info("Request received: createBranch"))
      .doOnNext(req -> {
        Set<ConstraintViolation<CreateBranchRequest>> violations = validator.validate(req);
        if (!violations.isEmpty()) {
          throw new ConstraintViolationException(violations);
        }
      })
      .flatMap(req -> createBranchUseCase.execute(req.getFranchiseId(), req.getName()))
      .flatMap(this::buildSuccessResponse)
      .onErrorResume(ConstraintViolationException.class, this::handleValidationException)
      .onErrorResume(DomainException.class, this::handleDomainException)
      .onErrorResume(BussinessException.class, this::handleBusinessException)
      .onErrorResume(Exception.class, this::handleGenericException)
      .doOnError(error -> log.error(GENERIC_ERROR_MESSAGE, error));
  }

  public Mono<ServerResponse> findBranchesByFranchise(ServerRequest serverRequest) {
    Long franchiseId = Long.valueOf(serverRequest.pathVariable("franchiseId"));
    return findBranchesByFranchiseUseCase.execute(franchiseId)
      .doOnSubscribe(s -> log.info("Request received: findBranchesByFranchise franchiseId={}", franchiseId))
      .collectList()
      .flatMap(this::buildSuccessResponse)
      .onErrorResume(DomainException.class, this::handleDomainException)
      .onErrorResume(BussinessException.class, this::handleBusinessException)
      .onErrorResume(Exception.class, this::handleGenericException)
      .doOnError(error -> log.error(GENERIC_ERROR_MESSAGE, error));
  }

  public Mono<ServerResponse> deleteBranch(ServerRequest serverRequest) {
    Long id = Long.valueOf(serverRequest.pathVariable("id"));
    return deleteBranchUseCase.execute(id)
      .doOnSubscribe(s -> log.info("Request received: deleteBranch id={}", id))
      .flatMap(ignored -> ServerResponse.noContent().build())
      .onErrorResume(DomainException.class, this::handleDomainException)
      .onErrorResume(BussinessException.class, this::handleBusinessException)
      .onErrorResume(Exception.class, this::handleGenericException)
      .doOnError(error -> log.error(GENERIC_ERROR_MESSAGE, error));
  }

  public Mono<ServerResponse> updateBranch(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(UpdateBranchRequest.class)
      .doOnSubscribe(s -> log.info("Request received: updateBranch"))
      .doOnNext(req -> {
        Set<ConstraintViolation<UpdateBranchRequest>> violations = validator.validate(req);
        if (!violations.isEmpty()) {
          throw new ConstraintViolationException(violations);
        }
      })
      .flatMap(req -> updateBranchUseCase.execute(req.getId(), req.getName()))
      .flatMap(this::buildSuccessResponse)
      .onErrorResume(ConstraintViolationException.class, this::handleValidationException)
      .onErrorResume(DomainException.class, this::handleDomainException)
      .onErrorResume(BussinessException.class, this::handleBusinessException)
      .onErrorResume(Exception.class, this::handleGenericException)
      .doOnError(error -> log.error(GENERIC_ERROR_MESSAGE, error));
  }

  public Mono<ServerResponse> createProduct(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(CreateProductRequest.class)
      .doOnSubscribe(s -> log.info("Request received: createProduct"))
      .doOnNext(req -> {
        Set<ConstraintViolation<CreateProductRequest>> violations = validator.validate(req);
        if (!violations.isEmpty()) {
          throw new ConstraintViolationException(violations);
        }
      })
      .flatMap(req -> createProductUseCase.execute(req.getBranchId(), req.getName(), req.getStock()))
      .flatMap(this::buildSuccessResponse)
      .onErrorResume(ConstraintViolationException.class, this::handleValidationException)
      .onErrorResume(DomainException.class, this::handleDomainException)
      .onErrorResume(BussinessException.class, this::handleBusinessException)
      .onErrorResume(Exception.class, this::handleGenericException)
      .doOnError(error -> log.error(GENERIC_ERROR_MESSAGE, error));
  }

  public Mono<ServerResponse> findProductsByBranch(ServerRequest serverRequest) {
    Long branchId = Long.valueOf(serverRequest.pathVariable("branchId"));
    return findProductsByBranchUseCase.execute(branchId)
      .doOnSubscribe(s -> log.info("Request received: findProductsByBranch branchId={}", branchId))
      .collectList()
      .flatMap(this::buildSuccessResponse)
      .onErrorResume(DomainException.class, this::handleDomainException)
      .onErrorResume(BussinessException.class, this::handleBusinessException)
      .onErrorResume(Exception.class, this::handleGenericException)
      .doOnError(error -> log.error(GENERIC_ERROR_MESSAGE, error));
  }

  public Mono<ServerResponse> updateProduct(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(UpdateProductRequest.class)
      .doOnSubscribe(s -> log.info("Request received: updateProduct"))
      .doOnNext(req -> {
        Set<ConstraintViolation<UpdateProductRequest>> violations = validator.validate(req);
        if (!violations.isEmpty()) {
          throw new ConstraintViolationException(violations);
        }
      })
      .flatMap(req -> updateProductUseCase.execute(req.getId(), req.getName(), req.getStock()))
      .flatMap(this::buildSuccessResponse)
      .onErrorResume(ConstraintViolationException.class, this::handleValidationException)
      .onErrorResume(DomainException.class, this::handleDomainException)
      .onErrorResume(BussinessException.class, this::handleBusinessException)
      .onErrorResume(Exception.class, this::handleGenericException)
      .doOnError(error -> log.error(GENERIC_ERROR_MESSAGE, error));
  }

  public Mono<ServerResponse> deleteProduct(ServerRequest serverRequest) {
    Long id = Long.valueOf(serverRequest.pathVariable("id"));
    return deleteProductUseCase.execute(id)
      .doOnSubscribe(s -> log.info("Request received: deleteProduct id={}", id))
      .flatMap(ignored -> ServerResponse.noContent().build())
      .onErrorResume(DomainException.class, this::handleDomainException)
      .onErrorResume(BussinessException.class, this::handleBusinessException)
      .onErrorResume(Exception.class, this::handleGenericException)
      .doOnError(error -> log.error(GENERIC_ERROR_MESSAGE, error));
  }

  public Mono<ServerResponse> getFranchiseWithBranchesTopProduct(ServerRequest serverRequest) {
    Long id = Long.valueOf(serverRequest.pathVariable("id"));
    return getFranchiseWithBranchesTopProductUseCase.execute(id)
      .doOnSubscribe(s -> log.info("Request received: getFranchiseWithBranchesTopProduct id={}", id))
      .flatMap(this::buildSuccessResponse)
      .onErrorResume(DomainException.class, this::handleDomainException)
      .onErrorResume(BussinessException.class, this::handleBusinessException)
      .onErrorResume(Exception.class, this::handleGenericException)
      .doOnError(error -> log.error(GENERIC_ERROR_MESSAGE, error));
  }

  private Mono<ServerResponse> buildSuccessResponse(Object response) {
    return ServerResponse.ok()
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(response);
  }

  private Mono<ServerResponse> handleValidationException(ConstraintViolationException ex) {
    String errorMessage = ex.getConstraintViolations().stream()
      .map(ConstraintViolation::getMessage)
      .collect(Collectors.joining(", "));

    log.warn("Validation error: {}", errorMessage);

    return ServerResponse.badRequest()
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(createErrorResponse(VALIDATION_ERROR_TEXT, errorMessage));
  }

  private Mono<ServerResponse> handleDomainException(DomainException ex) {
    log.warn("Domain error: {}", ex.getMessage());

    return ServerResponse.badRequest()
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(createErrorResponse(DOMAIN_ERROR_TEXT, ex.getMessage()));
  }

  private Mono<ServerResponse> handleBusinessException(BussinessException ex) {
    log.warn("Business error: {}", ex.getMessage());

    return ServerResponse.badRequest()
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(createErrorResponse(BUSINESS_ERROR_TEXT, ex.getMessage()));
  }

  private Mono<ServerResponse> handleGenericException(Exception ex) {
    log.error("Unexpected error", ex);

    return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(createErrorResponse(INTERNAL_ERROR_TEXT, GENERIC_ERROR_MESSAGE));
  }

  private ErrorResponse createErrorResponse(String error, String message) {
    return new ErrorResponse(error, message);
  }
}