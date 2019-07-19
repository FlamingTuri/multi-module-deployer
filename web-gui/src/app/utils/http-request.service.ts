import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

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
   */
  get<T>(url: string): Promise<T> {
    return this.httpClient.get<T>(url).toPromise();
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
