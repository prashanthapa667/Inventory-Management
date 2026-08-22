# pgsInventoryManagement

A simple product/inventory management REST API built with Spring Boot and MongoDB.
`entity -> repository -> service -> controller`.

## Product fields

| Field    | Type   | Notes                  |
|----------|--------|-------------------------|
| id       | String | Mongo-generated         |
| name     | String |                          |
| category | String | e.g. "Supplements", "Equipment" |
| sku      | String | stock keeping unit code |
| price    | double |                          |
| quantity | int    | units in stock           |

## Running it

1. Have MongoDB running locally on `27017`.
2. `./mvnw spring-boot:run`
3. API is available at `http://localhost:8082/product`

## Endpoints

### CRUD

| Method | Path                | Description              |
|--------|----------------------|---------------------------|
| GET    | /product/welcome     | Health check               |
| POST   | /product              | Create a product           |
| GET    | /product              | List all products          |
| GET    | /product/id/{myId}    | Get one product by id      |
| PUT    | /product/id/{myId}    | Update a product by id     |
| DELETE | /product/id/{myId}    | Delete a product by id     |

### Search & filter

| Method | Path                                     | Description                              |
|--------|--------------------------------------------|--------------------------------------------|
| GET    | /product/search?name=widget               | Case-insensitive partial name search      |
| GET    | /product/category/{category}               | All products in a category                 |
| GET    | /product/price?min=10&max=50               | Products within a price range              |
| GET    | /product/low-stock?threshold=5             | Products at or below a stock threshold (default 5) |

## Example requests

```
POST /product
{
  "name": "Whey Protein 2kg",
  "category": "Supplements",
  "sku": "SUP-WP-2KG",
  "price": 45.00,
  "quantity": 20
}

GET /product/search?name=protein
GET /product/category/Supplements
GET /product/price?min=20&max=60
GET /product/low-stock?threshold=3
```

## Notes vs. the original pgsEmpManagement sample

- Fixed the MongoDB config key: `spring.data.mongodb.uri` (the original used
  `spring.mongodb.uri`, which Spring Boot's autoconfiguration doesn't actually read).
- Runs on port `8082` so it can run alongside the original sample if needed.
- Added Spring Data derived-query methods on the repository (`findByNameContainingIgnoreCase`,
  `findByCategoryIgnoreCase`, `findByPriceBetween`, `findByQuantityLessThanEqual`) to back the
  search/filter endpoints — no manual query-writing needed.
