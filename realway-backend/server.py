from fastapi import FastAPI, WebSocket
from fastapi.websockets import WebSocketDisconnect
from pydantic import BaseModel
import asyncio, json

app = FastAPI()

class RequestData(BaseModel):
    uuid: str
    action: str

@app.get("/health")
def health():
    return {"status": "ok"}

@app.post("/update_client_data_park")
def update_client_data_park(body: RequestData):
    return {"message": f"park received for {body.uuid}", "tagNumber": "T-123", "status": "ok"}

@app.post("/update_client_data_navigate")
def update_client_data_navigate(body: RequestData):
    return {"message": f"navigate received for {body.uuid}", "tagNumber": "T-456", "status": "ok"}

@app.websocket("/ws")
async def ws_endpoint(ws: WebSocket):
    await ws.accept()
    try:
        # Read initial hello (optional)
        try:
            first = await ws.receive_text()
            print("Client hello:", first)
        except Exception:
            pass

        # First message your client expects
        await ws.send_text(json.dumps({"message": "success"}))

        # Demo navigation stream
        for m in [
            {"message": "Turn left in 5 m"},
            {"message": "Go straight for 10 m"},
            {"message": "Turn right in 3 m"},
            {"message": "Parked at Tag A12"},
            {"message": "Reached final destination"},
        ]:
            await asyncio.sleep(2)
            await ws.send_text(json.dumps(m))

        # Keep open to receive client replies
        while True:
            try:
                incoming = await ws.receive_text()
                print("Client says:", incoming)
            except WebSocketDisconnect:
                print("WebSocket disconnected")
                break
    except Exception as e:
        print("WS error:", e)
        await ws.close()
