package com.lgcns.hrm.cv.common.exception;


import com.lgcns.hrm.cv.common.constants.ErrorCodes;
import com.lgcns.hrm.cv.common.domain.ErrorCodeMapper;
import com.lgcns.hrm.cv.common.domain.Feedback;
import com.lgcns.hrm.cv.common.exception.feedback.*;
import org.apache.commons.collections4.MapUtils;

import java.util.LinkedHashMap;
import java.util.Map;
/**
 * <p>Description: Error code error code builder </p>
 * <p>
 * 1** Information, the server receives the request and needs the requester to continue performing the operation
 * 2** Success, the operation was successfully received and processed
 * 3** Redirect, further action is required to complete the request
 * 4** Client error, the request contains a syntax error or the request cannot be completed
 * 5** Server error. An error occurred while the server was processing the request.
 * <p>
 * Status codes starting with 1
 * 100 Continue to continue. The client should continue its request
 * 101 Switching Protocols switching protocols. The server switches protocols based on the client's request. You can only switch to a higher-level protocol, for example, switch to a new version of the HTTP protocol
 * <p>
 * Status codes starting with 2
 * 200 OK request successful. Generally used for GET and POST requests
 * 201 Created has been created. Successfully requested and created a new resource
 * 202 Accepted Accepted. Request accepted but not processed yet
 * 203 Non-Authoritative Information Non-authorized information. The request was successful. But the meta information returned is not on the original server, but a copy
 * 204 No Content No content. The server processed successfully, but no content was returned. Ensures that the browser continues to display the current document without updating the web page
 * 205 Reset Content Reset content. Server processing is successful and the user terminal (e.g. browser) should reset the document view. This return code clears the browser's form fields
 * 206 Partial Content Partial Content. The server successfully processed a partial GET request
 * <p>
 * Status codes starting with 3
 * 300 Multiple Choices. The requested resource may include multiple locations, and a list of resource characteristics and addresses may be returned accordingly for user terminal (e.g. browser) selection.
 * 301 Moved Permanently Moved permanently. The requested resource has been permanently moved to the new URI, the return information will include the new URI, and the browser will automatically be directed to the new URI. Any new requests in the future should use the new URI instead
 * 302 Found Temporarily moved. Similar to 301. But the resource is only moved temporarily. Clients should continue to use the original URI
 * 303 See Other See other addresses. Similar to 301. View using GET and POST requests
 * 304 Not Modified Not modified. The requested resource has not been modified. When the server returns this status code, no resource will be returned. Clients typically cache accessed resources by providing a header indicating that the client wishes to return only resources that have been modified after a specified date.
 * 305 Use Proxy uses a proxy. The requested resource must be accessed through a proxy
 * 306 Unused HTTP status code that has been abandoned
 * 307 Temporary Redirect Temporary redirect. Similar to 302. Redirect using GET request
 * <p>
 * Status codes starting with 4
 * 400 Bad Request The syntax of the client request is incorrect and the server cannot understand it.
 * 401 Unauthorized request requires user authentication
 * 402 Payment Required reserved for future use
 * 403 Forbidden The server understands the client's request, but refuses to execute the request
 * 404 Not Found The server cannot find the resource (web page) according to the client's request. Through this code, website designers can set up a personalized page that says "The resource you requested cannot be found"
 * 405 Method Not Allowed The method in the client request is prohibited
 * 406 Not Acceptable The server cannot complete the request based on the content characteristics requested by the client.
 * 407 Proxy Authentication Required request requires proxy identity authentication, similar to 401, but the requester should use a proxy for authorization
 * 408 Request Time-out The server waited too long for the request sent by the client and timed out.
 * 409 Conflict The server may return this code when completing the client's PUT request. A conflict occurred when the server processed the request.
 * 410 Gone The resource requested by the client no longer exists. 410 is different from 404. If the resource has been permanently deleted, the 410 code can be used. The website designer can specify the new location of the resource through the 301 code.
 * 411 Length Required The server cannot process the request information sent by the client without Content-Length.
 * 412 Precondition Failed The precondition for the client requesting information is wrong.
 * 413 Request Entity Too Large The request is rejected because the requested entity is too large and cannot be processed by the server. To prevent continued requests from the client, the server may close the connection. If the server cannot process it temporarily, a Retry-After response message will be included.
 * 414 Request-URI Too Large The requested URI is too long (URI is usually a URL) and the server cannot handle it.
 * 415 Unsupported Media Type The server cannot handle the media format attached to the request.
 * 416 Requested range not satisfiable The range requested by the client is invalid.
 * 417 Expectation Failed The server cannot satisfy the Expect request header information.
 * <p>
 * Status codes starting with 5
 * 500 Internal Server Error Server internal error, unable to complete the request
 * 501 Not Implemented The server does not support the requested function and cannot complete the request.
 * 502 Bad Gateway The server acting as a gateway or proxy received an invalid request from the remote server.
 * 503 Service Unavailable The server is temporarily unable to process the client's request due to overload or system maintenance. The length of the delay can be included in the server's Retry-After header.
 * 504 Gateway Time-out The server acting as a gateway or proxy did not obtain the request from the remote server in time.
 * 505 HTTP Version not supported The server does not support the requested HTTP protocol version and cannot complete the processing.
 *
 */
public class ErrorCodeMapperBuilder {

    private final Map<Feedback, Integer> unauthorizedConfigs = new LinkedHashMap<>() {{
        put(ErrorCodes.UNAUTHORIZED, ErrorCodes.UNAUTHORIZED.getSequence());
    }};

    private final Map<Feedback, Integer> forbiddenConfigs = new LinkedHashMap<>() {{
        put(ErrorCodes.FORBIDDEN, ErrorCodes.FORBIDDEN.getSequence());
    }};

    private final Map<Feedback, Integer> methodNotAllowedConfigs = new LinkedHashMap<>() {{
        put(ErrorCodes.METHOD_NOT_ALLOWED, ErrorCodes.METHOD_NOT_ALLOWED.getSequence());
    }};

    private final Map<Feedback, Integer> notAcceptableConfigs = new LinkedHashMap<>() {{
        put(ErrorCodes.NOT_ACCEPTABLE, ErrorCodes.NOT_ACCEPTABLE.getSequence());
    }};

    private final Map<Feedback, Integer> preconditionFailedConfigs = new LinkedHashMap<>() {{
        put(ErrorCodes.PRECONDITION_FAILED, ErrorCodes.PRECONDITION_FAILED.getSequence());
    }};


    private final Map<Feedback, Integer> unsupportedMediaTypeConfigs = new LinkedHashMap<>() {{
        put(ErrorCodes.PRECONDITION_FAILED, ErrorCodes.PRECONDITION_FAILED.getSequence());
    }};

    private final Map<Feedback, Integer> internalServerErrorConfigs = new LinkedHashMap<>() {{
        put(ErrorCodes.INTERNAL_SERVER_ERROR, ErrorCodes.INTERNAL_SERVER_ERROR.getSequence());
    }};

    private final Map<Feedback, Integer> notImplementedConfigs = new LinkedHashMap<>() {{
        put(ErrorCodes.NOT_IMPLEMENTED, ErrorCodes.NOT_IMPLEMENTED.getSequence());
    }};

    private final Map<Feedback, Integer> serviceUnavailableConfigs = new LinkedHashMap<>() {{
        put(ErrorCodes.SERVICE_UNAVAILABLE, ErrorCodes.SERVICE_UNAVAILABLE.getSequence());
    }};

    private final Map<Integer, Map<Feedback, Integer>> customizeConfigs = new LinkedHashMap<>();

    private ErrorCodeMapperBuilder create(Map<Feedback, Integer> container, Feedback... items) {
        for (Feedback item : items) {
            container.put(item, item.getSequence(container.size()));
        }
        return this;
    }

    public ErrorCodeMapperBuilder unauthorized(UnauthorizedFeedback... items) {
        return create(this.unauthorizedConfigs, items);
    }


    public ErrorCodeMapperBuilder forbidden(ForbiddenFeedback... items) {
        return create(this.forbiddenConfigs, items);
    }


    public ErrorCodeMapperBuilder methodNotAllowed(MethodNotAllowedFeedback... items) {
        return create(this.methodNotAllowedConfigs, items);
    }

    public ErrorCodeMapperBuilder notAcceptable(NotAcceptableFeedback... items) {
        return create(this.notAcceptableConfigs, items);
    }


    public ErrorCodeMapperBuilder preconditionFailed(PreconditionFailedFeedback... items) {
        return create(this.preconditionFailedConfigs, items);
    }

    public ErrorCodeMapperBuilder unsupportedMediaType(UnsupportedMediaTypeFeedback... items) {
        return create(this.unsupportedMediaTypeConfigs, items);
    }

    public ErrorCodeMapperBuilder internalServerError(InternalServerErrorFeedback... items) {
        return create(this.internalServerErrorConfigs, items);
    }

    public ErrorCodeMapperBuilder notImplemented(NotImplementedFeedback... items) {
        return create(this.notImplementedConfigs, items);
    }

    public ErrorCodeMapperBuilder serviceUnavailable(ServiceUnavailableFeedback... items) {
        return create(this.serviceUnavailableConfigs, items);
    }

    public ErrorCodeMapperBuilder customize(CustomizeFeedback... items) {
        for (Feedback item : items) {
            if (item.isCustom()) {
                Map<Feedback, Integer> config = customizeConfigs.get(item.getCustom());
                if (MapUtils.isEmpty(config)) {
                    config = new LinkedHashMap<>();
                }
                config.put(item, item.getSequence(config.size()));
                customizeConfigs.put(item.getCustom(), config);
            }
        }
        return this;
    }

    public ErrorCodeMapper build() {
        ErrorCodeMapper errorCodeMapper = ErrorCodeMapper.getInstance();
        errorCodeMapper.append(unauthorizedConfigs);
        errorCodeMapper.append(forbiddenConfigs);
        errorCodeMapper.append(methodNotAllowedConfigs);
        errorCodeMapper.append(notAcceptableConfigs);
        errorCodeMapper.append(preconditionFailedConfigs);
        errorCodeMapper.append(unsupportedMediaTypeConfigs);
        errorCodeMapper.append(internalServerErrorConfigs);
        errorCodeMapper.append(notImplementedConfigs);
        errorCodeMapper.append(serviceUnavailableConfigs);

        customizeConfigs.forEach((key, feedbacks) -> errorCodeMapper.append(feedbacks));
        return errorCodeMapper;
    }
}
