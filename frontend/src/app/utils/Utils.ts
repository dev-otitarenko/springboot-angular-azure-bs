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
          let _message: string = `[${error.status}] \"${error.url}\": ${error.statusText}`;
          if (error.message) _message += `\nMessage: ${error.message}`;
          if (error.error) {
            if (error.error.exception) _message += `\nException: ${error.error.exception}`;
            if (error.error.message) _message += `\nAdditional: ${error.error.message}`;
            if (error.error.errors) {
              const _errors = error.error.errors;
              _message += "\nDetail: ";
              for (let _k in _errors) {
                _message += ", " + _errors[_k];
              }
            }
          }
          return { severity: 'error', summary: title || 'Error', detail: _message }
        } catch (_) {
          return { severity: 'error', summary: title || 'Error', detail: error }
        }
      }
  }
}
