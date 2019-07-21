import { Component, Input, OnInit } from '@angular/core';
import { HttpRequestService } from '../utils/http-request.service';

@Component({
  selector: 'app-micro-service-interaction',
  templateUrl: './micro-service-interaction.component.html',
  styleUrls: ['./micro-service-interaction.component.scss']
})
export class MicroServiceInteractionComponent implements OnInit {

  @Input() serviceRole: string;
  @Input() servicePort: number;
  @Input() countRoute: string;
  @Input() countRetriever: (res) => number;
  private serviceAddress: string;
  count: number = undefined;

  constructor(private httpRequestService: HttpRequestService) {
  }

  ngOnInit() {
    this.serviceAddress = `http://127.0.0.1:${this.servicePort}`;
  }

  getServiceStatus() {
    const options = {responseType: 'text'};
    this.httpRequestService.get<any>(`${this.serviceAddress}/api/status`, options)
      .then(res => console.log(res));
  }

  getCount() {
    this.getServiceCount(this.httpRequestService, this.serviceAddress + this.countRoute,
      this.countRetriever).then(res => this.count = res);
  }

  getServiceCount(httpRequestService: HttpRequestService, url: string,
                  resultResolverFunction: (res) => any): Promise<any> {
    const newPromise = value => new Promise(resolve => resolve(value));
    return httpRequestService.get<any>(url).then(res => {
      return newPromise(resultResolverFunction(res));
    }).catch(() => {
      return newPromise(undefined);
    });
  }

}
