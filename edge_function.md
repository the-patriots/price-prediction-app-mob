## Edge Function

#### Function Name:
predict_price

#### Endpoint
POST https://oapcvaoyfjcyogdalsxq.supabase.co/functions/v1/predict_price

### Headers
```json
{
  "authorization": "Bearer $SUPABASE_PUBLISHABLE_KEY",
  "apikey": "$SUPABASE_PUBLISHABLE_KEY",
  "content-type": "application/json"
}
```

### Request Body
```json
{
  "category": "string",
  "price": 1000,
  "productName": "string"
}
```
prediction result: enum
<br>
"Normal", "Di bawah rata-rata", "Di atas rata-rata"

### Response Body
```json
{
  "prediction": "string"
}
```