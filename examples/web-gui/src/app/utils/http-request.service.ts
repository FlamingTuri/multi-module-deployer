import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HttpRequestService {

  constructor(private httpClient: HttpClient) {
  }

  /**
   * Makes a GET http request
   *
   * @param url request's url
   * @param options the request options
   */
  get<T>(url: string, options: {} = null): Promise<T> {
    let result: Observable<T>;
    if (options) {
      result = this.httpClient.get<T>(url, options);
    } else {
      result = this.httpClient.get<T>(url);
    }
    return result.toPromise();
  }

  /**
   * Makes a POST http request
   *
   * @param url request's url
   * @param body request's body
   */
  post<T>(url: string, body): Promise<T> {
    return this.httpClient.post<T>(url, body).toPromise();
  }

  /**
   * Makes a PUT http request
   *
   * @param url request's url
   * @param body request's body
   */
  put<T>(url: string, body): Promise<T> {
    return this.httpClient.put<T>(url, body).toPromise();
  }

  /**
   * Makes a DELETE http request
   *
   * @param url request's url
   */
  delete<T>(url: string): Promise<T> {
    return this.httpClient.delete<T>(url).toPromise();
  }
}
