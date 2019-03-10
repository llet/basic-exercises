```json
{"url":"/user","description":"登录接口","method":"POST","version":"v1","request":[{"property":"userName","type":"String","default":"","constraint":"1&2","description":"用户名"},{"property":"password","type":"String","default":"","constraint":"3","description":"密码"},{"property":"city","type":"String","default":"null","constraint":null,"description":"城市"}],"response":[{"property":"code","type":"Number","default":"200","constraint":"","description":"返回码"},{"property":"message","type":"String","default":"成功","constraint":"","description":"返回信息"},{"property":"testFiled","type":"String","default":"测试返回一个字段","constraint":"","mock_code":"Random.getUUID()","description":"返回信息"},],"constraints":[{"cid":"0","constraint":"!-null","code":100,"error":"字段{}不能为空"},{"cid":"1","constraint":"!-e","code":101,"error":"用户名已存在"},{"cid":"2","constraint":"~^\w+$","code":102,"error":"用户名格式不正确"},{"cid":"3","constraint":"~^\w+$","code":103,"error":"密码格式不正确"},],"see":"https://developer.github.com/v3/"}
```



### Client errors

Sending invalid JSON will result in a 400 Bad Request response.

```http
HTTP/1.1 400 Bad Request
Content-Length: 35

{"message":"Problems parsing JSON"}
```

Sending the wrong type of JSON values will result in a `400 Bad Request` response.

```http
HTTP/1.1 400 Bad Request
Content-Length: 40

{"message":"Body should be a JSON object"}
```

Sending invalid fields will result in a `422 Unprocessable Entity` response.

```http
HTTP/1.1 422 Unprocessable Entity
Content-Length: 149

{"message":"Validation Failed","errors":[{"resource":"Issue","field":"title","code":"missing_field"}]}
```

https://developer.github.com/v4/guides/using-global-node-ids/