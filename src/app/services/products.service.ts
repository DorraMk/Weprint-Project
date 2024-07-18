import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Product } from '../models/Product';
import { User } from '../models/User';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {

    constructor(private http : HttpClient) { }

    getProducts () : Observable<Product[]> {
        return this.http.get<Product[]>(`${environment.API_URL}/api/products`);
    }

    getProduct (id : string) : Observable<Product> {
        return this.http.get<Product>(`${environment.API_URL}/api/products/${id}`);
    }
    
    create(data: any): Observable<any> {
        const url="http://localhost:8080/api/addproduct" ;
        return this.http.post(url, data);
      }

      ApproveCommande (userId : string, productId : string, state : boolean) : Observable<User> {
        return this.http.put<User>(`${environment.API_URL}/api/users/${userId}/cart/approve/${productId}`, {
            state
        })
    }




}
