import styles from './stylesheets/DeviceCard.module.css';
import coreConstants from "../CoreConstants.jsx";

function DeviceCard({name, isOnline, isArmed, id}) {

    const statusImg = isOnline ? coreConstants.CHECK_MARK_IMG_PATH : coreConstants.CROSS_MARK_IMG_PATH;
    const deviceId = id;

    function deleteDevice() {
        fetch(
            `${coreConstants.DEVICES_URL}/${deviceId}`,
            {method: "DELETE"}
        ).catch(
            (error) => {alert(`Error deleting device with id ${deviceId}: ${error.message}`)}
        )

    }

    return (
        <div className={styles.deviceCard}>
            <img src = {statusImg} alt={"Device Online"}/>
            <div>
                <p className={styles.deviceCardText}>Device Name: <b>{`\n${name}`}</b></p>
                <p className={styles.deviceCardText}>Status: <b>{isOnline ? "Online": "Offline"}</b></p>
                <p className={isArmed ? styles.armingTextArmed: styles.armingTextDisarmed}><span>{isArmed ? "Armed" : "Disarmed"}</span></p>
                <button className={styles.deleteDeviceButton} onClick={deleteDevice}>DELETE DEVICE</button>
            </div>
        </div>
    )
}

export default DeviceCard;