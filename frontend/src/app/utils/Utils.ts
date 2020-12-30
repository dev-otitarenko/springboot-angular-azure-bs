export class ApiError {
  status: string;
  message: string;
  errors: string[];
}

export class UIUtils {
  static getMessageVar(error: any, title?: string): any {
      if (error.status == 400 || // Bad Request
        error.status == 403 || // Forbidden
        error.status == 404) {// /NotFound
        let apiError: ApiError = error.error;
        let _message: string = `[${error.status}] "${error.url}": ${error.statusText}`;
        if (apiError.message != "") _message = `${_message}, Message: ${apiError.message}`;
        let _errors = apiError.errors;
        if (_errors) {
          _message += "\nDetail: ";
          for (let _k in _errors) {
            _message += ", " + _errors[_k];
          }
        }

        return { severity: apiError.status === "error" ? 'error' : 'warn', summary: title || 'Error', detail: _message }
      } else {
        try {
          let _message: string = "<b>Status:</b> [" + error.status + "] " + error.statusText + " " + error.url + "<br /><b>Message:</b> " + error.message;
          if (error.error) {
            if (error.error.exception) _message += "<br /><b>Exception: </b>" + error.error.exception;
            if (error.error.message) _message += "<br /><b>Detail message: </b>" + error.error.message;
            let _errors = error.error.errors;
            if (_errors) {
              _message += "<br /><b>Detail info:</b><ul>";
              for (let _k in _errors) {
                _message += "<li>" + _errors[_k] + "</li>";
              }
              _message += "</ul>";
            }
          }
          return { severity: 'error', summary: title || 'Error', detail: _message }
        } catch (_) {
          return { severity: 'error', summary: title || 'Error', detail: error }
        }
      }
  }
}
