import {useState} from "react";
import styles from "./ArmingButton.module.css";

function ArmingButton() {

    const [isArmed, setIsArmed] = useState(false);

    return (
        <div className = {styles.center}>
            <button className={isArmed ? styles.armedButton : styles.disarmedButton} onClick={() => {setIsArmed(!isArmed)}}>
                {isArmed ? "Armed" : "Disarmed"}
            </button>
        </div>
    )

}

export default ArmingButton;