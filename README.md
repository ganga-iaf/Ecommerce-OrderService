# Order Service

* Order service handles all requests related to orders like creating an order, cancelling an order, getting list of all orders made by requested user, details of particular order and getting the status of order using payment gateway session id.
* While placing a new order, to process the payment, order service connects to the payment service which there after connects to the payment gateway.
* All requests made to the order service must include **Authorization** header with bearer JWT token for user authentication and validation.

**API Endpoints**

## 1.`[GET]` api/orders
To get the list of all past orders placed by the requested user.

Request Header(s)  
  * Authorization: Bearer [JWT token]

Sample Response:

```json
[
  {
    "orderId": 1,
    "orderDate": "2025-07-11T12:19:13.901+00:00",
    "amount": 30000.0,
    "orderStatus": "Created",
    "orderItemDtos": [
      {
        "productId": 1,
        "productNameSnapshot": "Iphone 15",
        "unitPrice": 15000.0,
        "quantity": 2,
        "orderDate": null
      }
    ]
  },
  {
    "orderId": 2,
    "orderDate": "2025-07-11T12:20:21.634+00:00",
    "amount": 150000.0,
    "orderStatus": "Cancelled",
    "orderItemDtos": [
      {
        "productId": 10,
        "productNameSnapshot": "Lenovo ThinkPad E15",
        "unitPrice": 75000.0,
        "quantity": 2,
        "orderDate": null
      }
    ]
  }
]
```

## 2.`[GET]` api/orders/{orderId}
To get the details of a particular order.

Request Header(s):
* Authorization: Bearer [JWT token]

Path Variable(s):
* orderId*  :  This is id of the order that you are intended to get details.

Sample Response:

```json
{
	"orderId": 2,
	"orderDate": "2025-07-11T12:20:21.634+00:00",
	"amount": 150000.0,
	"orderStatus": "Cancelled",
	"orderItemDtos": [
		{
			"productId": 10,
			"productNameSnapshot": "Lenovo ThinkPad E15",
			"unitPrice": 75000.0,
			"quantity": 2
		}
	]
}
```

## 3.[POST] api/orders
To place an order

Request Header(s):
* Authorization: Bearer [JWT token]

Sample request body:
```json
{
	"orderItemDtos":[
	{
		"productId":10,
	  "productNameSnapshot":"Lenovo ThinkPad E15",
	  "unitPrice":75000,
	  "quantity":2
  }
]}
```

Sample response body:
```json
{
  "paymentLink":"https://checkout.stripe.com/..."
}
```


4.`[PUT]` api/orders/{orderId}  
To cancel the placed order.

Request Header(s):
* Authorization: Bearer [JWT token]

Path Variable(s):
* orderId*  :  This is the id of the order that you are intended to cancel.

Sample response body:
```json
{
	"orderId": 2,
	"orderDate": "2025-07-11T12:20:21.634+00:00",
	"amount": 150000.0,
	"orderStatus": "Cancelled",
	"orderItemDtos": [
		{
			"productId": 10,
			"productNameSnapshot": "Lenovo ThinkPad E15",
			"unitPrice": 75000.0,
			"quantity": 2
		}
	]
}
```

5.`[GET]` api/orders/paymentstatus/{session_id}   
To get the status of placed order using payment gateway session id.

Request Header(s):
* Authorization: Bearer [JWT token]

Path Variable(s):
* session_id*  :  This is the id of payment gateway session, create during payment process of the order. we get this id from the redirected url after payment success/failure

```json
{
	"orderId": 30,
	"orderStatus": "Confirmed"
}
```

### Database Tables

* orders
  * id
  * user_id
  * amount
  * order_status
  * created_at
  * updated_at  

    
* order_item
  * id
  * product_id
  * product_name_sanpshot
  * unit_price
  * quantity
  * created_at
  * updated_at

### Technologies
* Java17
* Spring Boot
* My SQL DB

### Maven dependencies
* lombok
* mysql-connector-j
* spring-boot-starter-data-jpa
* jjwt-api
* jjwt-impl
* jjwt-jackson




