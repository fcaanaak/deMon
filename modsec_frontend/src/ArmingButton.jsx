import {useState} from "react";
import styles from "./ArmingButton.module.css";
import coreConstants from "./CoreConstants";

function ArmingButton() {

    const [isArmed, setIsArmed] = useState(false);

    function sendArmingRequest() {
        return fetch(coreConstants.ARMING_URL, {
            method: "PUT",
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify({"isArmed": !isArmed}),
        })
    }

    function armDevices() {
        sendArmingRequest()
            .then(response => response.json())
            .then(respJson => setIsArmed(respJson.isArmed))
            .catch(err => alert("Arming Error: " + err));
    }

    return (
        <div className = {styles.center}>
            <button className={isArmed ? styles.armedButton : styles.disarmedButton} onClick={armDevices}>
                {isArmed ? "Armed" : "Disarmed"}
            </button>
        </div>
    )

}

export default ArmingButton;