import React from "react";
import { PieChart } from "@mui/x-charts/PieChart";
import * as AssetAPI from "../utility/AssetAPI";

export default function AssetPieChart() {
  const [assets, setAssets] = React.useState([]);

  React.useEffect(() => {
    const getAssets = async () => {
      const res = await AssetAPI.getAll();
      setAssets(res);
    };
    getAssets();
  }, []);

  // Count occurrences of each asset type
  const assetCounts = assets.reduce((counts, asset) => {
    counts[asset.type] = (counts[asset.type] || 0) + 1;
    return counts;
  }, {});

  // Convert counts to series data for PieChart
  const seriesData = Object.entries(assetCounts).map(([type, count]) => ({
    id: type,
    value: count,
    label: type,
  }));

  return (
    <div style={{ width: "100%", overflow: "hidden" }}>
      <PieChart
        series={[
          {
            data: seriesData,
          },
        ]}
        height={200} // Set the desired height
      />
    </div>
  );
}
