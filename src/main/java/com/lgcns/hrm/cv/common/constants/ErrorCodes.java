package com.lgcns.hrm.cv.common.constants;

import com.lgcns.hrm.cv.common.exception.feedback.*;

public interface ErrorCodes {

    /**
     *200
     */
    OkFeedback OK = new OkFeedback("Success");
    /**
     *204
     */
    NoContentFeedback NO_CONTENT = new NoContentFeedback("No content");
    /**
     * 401.** Unauthorized Unauthorized request requires user authentication
     */
    UnauthorizedFeedback UNAUTHORIZED = new UnauthorizedFeedback("Unauthorized");
    UnauthorizedFeedback ACCESS_DENIED = new UnauthorizedFeedback("You do not have permission, access is denied");
    UnauthorizedFeedback ACCOUNT_DISABLED = new UnauthorizedFeedback("This account has been disabled");
    UnauthorizedFeedback ACCOUNT_ENDPOINT_LIMITED = new UnauthorizedFeedback("You have logged in using other terminals, please log out of other terminals first");
    UnauthorizedFeedback ACCOUNT_EXPIRED = new UnauthorizedFeedback("This account has expired");
    UnauthorizedFeedback ACCOUNT_LOCKED = new UnauthorizedFeedback("This account has been locked");
    UnauthorizedFeedback BAD_CREDENTIALS = new UnauthorizedFeedback("Wrong username or password");
    UnauthorizedFeedback CREDENTIALS_EXPIRED = new UnauthorizedFeedback("The account password certificate has expired");
    UnauthorizedFeedback INVALID_CLIENT = new UnauthorizedFeedback("Client authentication failed or the database is not initialized");
    UnauthorizedFeedback INVALID_TOKEN = new UnauthorizedFeedback("The access token provided has expired, been revoked, is malformed, or is invalid");
    UnauthorizedFeedback INVALID_GRANT = new UnauthorizedFeedback("The provided authorization grant or refresh token is invalid, expired, or revoked");
    UnauthorizedFeedback UNAUTHORIZED_CLIENT = new UnauthorizedFeedback("The client does not have the authority to use this method to request an authorization code or access token");
    UnauthorizedFeedback USERNAME_NOT_FOUND = new UnauthorizedFeedback("Wrong username or password");
    UnauthorizedFeedback SESSION_EXPIRED = new UnauthorizedFeedback("Session has expired, please refresh the page before using it again");

    /**
     * 403.** Forbidden request, corresponding to 403
     */
    ForbiddenFeedback FORBIDDEN = new ForbiddenFeedback("Forbidden request");
    ForbiddenFeedback INSUFFICIENT_SCOPE = new ForbiddenFeedback("TOKEN has insufficient permissions, you need a higher level of permissions");
    ForbiddenFeedback SQL_INJECTION_REQUEST = new ForbiddenFeedback("Suspected SQL injection request");

    NotFoundFeedback NOT_FOUND = new NotFoundFeedback("The requested resource does not exist");
    /**
     * 405.** Method not allowed corresponds to 405
     */
    MethodNotAllowedFeedback METHOD_NOT_ALLOWED = new MethodNotAllowedFeedback("Method not allowed");
    MethodNotAllowedFeedback HTTP_REQUEST_METHOD_NOT_SUPPORTED = new MethodNotAllowedFeedback("The requested method type is not supported");

    /**
     * 406.** Request not accepted, corresponding to 406
     */
    NotAcceptableFeedback NOT_ACCEPTABLE = new NotAcceptableFeedback("Not accepted request");
    NotAcceptableFeedback UNSUPPORTED_GRANT_TYPE = new NotAcceptableFeedback("The authorization server does not support the authorization grant type");
    NotAcceptableFeedback UNSUPPORTED_RESPONSE_TYPE = new NotAcceptableFeedback("The authorization server does not support using this method to obtain authorization codes or access tokens");
    NotAcceptableFeedback UNSUPPORTED_TOKEN_TYPE = new NotAcceptableFeedback("The authorization server does not support revoking the provided token type");

    /**
     * 412.* Unauthorized Precondition Failed The prerequisite error for the client to request information
     */
    PreconditionFailedFeedback PRECONDITION_FAILED = new PreconditionFailedFeedback("The prerequisite for the client requesting information is wrong");
    PreconditionFailedFeedback INVALID_REDIRECT_URI = new PreconditionFailedFeedback("The value of the OAuth2 URI redirection is invalid");
    PreconditionFailedFeedback INVALID_REQUEST = new PreconditionFailedFeedback("Invalid request, wrong or invalid parameters.");
    PreconditionFailedFeedback INVALID_SCOPE = new PreconditionFailedFeedback("Authorization scope error");

    /**
     * 415.* Unsupported Media Type The server cannot handle the media format attached to the request
     */
    UnsupportedMediaTypeFeedback UNSUPPORTED_MEDIA_TYPE = new UnsupportedMediaTypeFeedback("The server cannot handle the media format attached to the request");
    UnsupportedMediaTypeFeedback HTTP_MEDIA_TYPE_NOT_ACCEPTABLE = new UnsupportedMediaTypeFeedback("Unsupported Media Type");

    /**
     * 500.* Internal Server Error Server internal error, unable to complete the request
     */
    InternalServerErrorFeedback INTERNAL_SERVER_ERROR = new InternalServerErrorFeedback("Internal server error, unable to complete the request");
    InternalServerErrorFeedback SERVER_ERROR = new InternalServerErrorFeedback("The authorization server encountered an unexpected situation and was unable to satisfy the request");
    InternalServerErrorFeedback HTTP_MESSAGE_NOT_READABLE_EXCEPTION = new InternalServerErrorFeedback("Error deserializing JSON string into entity!");
    InternalServerErrorFeedback ILLEGAL_ARGUMENT_EXCEPTION = new InternalServerErrorFeedback("Illegal parameter error, please carefully confirm whether the parameter is used correctly.");
    InternalServerErrorFeedback IO_EXCEPTION = new InternalServerErrorFeedback("IO exception");
    InternalServerErrorFeedback MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION = new InternalServerErrorFeedback("The interface parameters are incorrectly used or necessary parameters are missing, please consult the interface documentation!");
    InternalServerErrorFeedback NULL_POINTER_EXCEPTION = new InternalServerErrorFeedback("A null value occurred during the execution of the background code");
    InternalServerErrorFeedback TYPE_MISMATCH_EXCEPTION = new InternalServerErrorFeedback("Type mismatch");
    InternalServerErrorFeedback BORROW_OBJECT_FROM_POOL_ERROR_EXCEPTION = new InternalServerErrorFeedback("Error in getting object from object pool");

    /**
     * 501. Not Implemented The server does not support the requested function and cannot complete the request.
     */
    NotImplementedFeedback NOT_IMPLEMENTED = new NotImplementedFeedback("The server does not support the requested function and cannot complete the request");
    NotImplementedFeedback PROPERTY_VALUE_IS_NOT_SET = new NotImplementedFeedback("The necessary Property configuration attribute value is not set");
    NotImplementedFeedback URL_FORMAT_INCORRECT = new NotImplementedFeedback("URL format error or missing HTTP protocol header");
    NotImplementedFeedback ILLEGAL_SYMMETRIC_KEY = new NotImplementedFeedback("Static AES encryption algorithm KEY is illegal");

    /**
     * 503.* Service Unavailable Due to overload or system maintenance, the server is temporarily unable to process the client's request. The length of the delay can be included in the server's Retry-After header.
     */
    ServiceUnavailableFeedback SERVICE_UNAVAILABLE = new ServiceUnavailableFeedback("Service Unavailable");
    ServiceUnavailableFeedback COOKIE_THEFT = new ServiceUnavailableFeedback("Cookie information is not secure");
    ServiceUnavailableFeedback INVALID_COOKIE = new ServiceUnavailableFeedback("Unavailable Cookie information");
    ServiceUnavailableFeedback PROVIDER_NOT_FOUND = new ServiceUnavailableFeedback("Authorization server code logic configuration error");
    ServiceUnavailableFeedback TEMPORARILY_UNAVAILABLE = new ServiceUnavailableFeedback("Due to temporary server overload or maintenance, the authorization server is currently unable to process this request");
    ServiceUnavailableFeedback SEARCH_IP_LOCATION = new ServiceUnavailableFeedback("A read error occurred while searching for IP location, and the server is currently unable to process the request");
    /**
     * 6*.* are errors related to data operations
     */
    CustomizeFeedback TRANSACTION_ROLLBACK = new CustomizeFeedback("Data transaction processing failed, data rolled back", 6);
    CustomizeFeedback METHOD_ARGUMENT_NOT_VALID = new CustomizeFeedback("Interface parameter verification failed, the parameter was used incorrectly or the parameter was not received", 6);
    CustomizeFeedback BAD_SQL_GRAMMAR = new CustomizeFeedback("Low-level SQL syntax error, check whether SQL can run normally or whether the field name is correct", 6);
    CustomizeFeedback DATA_INTEGRITY_VIOLATION = new CustomizeFeedback("This data is being referenced by other data, please delete the reference relationship first, and then perform the data deletion operation", 6);

    /**
     * 7*.* Infrastructure interaction errors
     * 71.* An error occurred in Redis operation
     * 72.* Cache operation error occurred
     */
    CustomizeFeedback PIPELINE_INVALID_COMMANDS = new CustomizeFeedback("The Redis pipeline contains one or more invalid commands", 7);
}
